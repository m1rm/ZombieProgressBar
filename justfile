set shell := ["bash", "-cu"]

# Run Docker as local user so build/.gradle are owned by you
export UID := `id -u`
export GID := `id -g`
# X server auth cookie, mounted into the container by run-ide
export XAUTHORITY := env("XAUTHORITY", home_directory() / ".Xauthority")

[private]
default:
	just --list

# Create the Gradle cache dir up front, otherwise Docker creates it owned by root
[private]
gradle-cache:
	mkdir -p .gradle-docker

# Build the plugin zip using Docker (no local Java/Gradle needed)
build: gradle-cache
	docker compose run --rm builder ./gradlew buildPlugin

# Check compatibility with all supported IDE releases using the IntelliJ Plugin Verifier
verify: gradle-cache
	docker compose run --rm builder ./gradlew verifyPlugin

# Run the IDE with the plugin in a sandbox (requires an X11 or Xwayland display on the host)
run-ide: gradle-cache
	@test -f "$XAUTHORITY" || { echo "X auth cookie not found at $XAUTHORITY, set XAUTHORITY"; exit 1; }
	docker compose run --rm --build ide ./gradlew runIde

# Publish the plugin to JetBrains Marketplace.
# Requires PUBLISH_TOKEN (and signing env vars) set on the host.
publish: gradle-cache
	docker compose run --rm \
		-e PUBLISH_TOKEN \
		-e CERTIFICATE_CHAIN \
		-e PRIVATE_KEY \
		-e PRIVATE_KEY_PASSWORD \
		builder ./gradlew publishPlugin

# Clean Gradle and build outputs
clean: gradle-cache
	docker compose run --rm builder ./gradlew clean
