.DEFAULT_GOAL := compile-run
compile-run: compile run

compile: clean
	javac -d ./target/classes ./src/main/java/games/Slot.java

run:
	java -cp ./target/classes games.Slot

clean:
	rd /s /q target