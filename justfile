set shell := ["bash", "-cu"]

# Run Docker as local user so build/.gradle are owned by you
export UID := `id -u`
export GID := `id -g`

[private]
default:
	just --list

# Build the plugin zip using Docker (no local Java/Gradle needed)
build:
	docker compose run --rm builder ./gradlew buildPlugin

# Run the IDE with the plugin in a sandbox (requires X11/GUI support on host)
run-ide:
	docker compose run --rm \
		-e DISPLAY=${DISPLAY:-:0} \
		-v /tmp/.X11-unix:/tmp/.X11-unix \
		builder ./gradlew runIde

# Publish the plugin to JetBrains Marketplace.
# Requires PUBLISH_TOKEN (and signing env vars) set on the host.
publish:
	docker compose run --rm \
		-e PUBLISH_TOKEN \
		-e CERTIFICATE_CHAIN \
		-e PRIVATE_KEY \
		-e PRIVATE_KEY_PASSWORD \
		builder ./gradlew publishPlugin

# Clean Gradle and build outputs
clean:
	docker compose run --rm builder ./gradlew clean

