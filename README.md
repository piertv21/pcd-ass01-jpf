# JPF Analysis of the PCD Assignment #01

### Usage
The structure of the project is as follows:
- `src/main/java`: contains the Java program to be analyzed
- `src/main/jpf`: contains the JPF configuration file
- `lib`: contains the JPF libraries used by the project to analyze the Java program

The gradle build script create a task for each JPF configuration file in the `src/main/jpf` directory.
The task name is `run<ConfigName>Verify`, where `<ConfigName>` is the name of the configuration file.
For instance, `src/main/jpf/MyConfig.jpf` can be run with the command `./gradle runMyConfigVerify`.

## How it works
It uses the gradle toolchain to use the right JDK (8) and run the JPF analysis.
