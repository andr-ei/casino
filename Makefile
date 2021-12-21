.DEFAULT_GOAL := build-run
compile-run: compile run

build-run: build
	java -jar ./target/casino.jar

compile: clean
	javac -d ./target/classes ./src/main/java/games/Slot.java

run:
	java -cp ./target/classes games.Slot

clean:
	rd /s /q target

build: compile
	jar cfe ./target/casino.jar games.Slot -C ./target/classes .