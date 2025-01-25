#Path separator is different on Windows versus Unix based OSes
ifeq ($(OS),Windows_NT)
	SEP=;
else
	SEP=:
endif

JFLAGS = -g
JCLASS = -cp "src:.:../junit-4.5.jar"
JC = javac
JVM = java

.PHONY: test doc expt

test:
	find . -name '*.class' -exec rm -f {} \;
	$(JC) $(JCLASS) $(JFLAGS) src/AllTests.java
	$(JVM) $(JCLASS) org.junit.runner.JUnitCore src.AllTests

doc:
	doxygen doxConfig
	cd latex && $(MAKE)

demo:
	$(JC) $(JCLASS) $(JFLAGS) src/Demo.java
	$(JVM) src/Demo

clean:
	rm -rf html
	rm -rf latex
	cd src
	rm **/*.class