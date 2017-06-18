1. clone the project
2. intellij > open > .../build.gradle > open as project (intellij will import gradle modules)
3. execute 'compileJava' or 'generateProto' task and files will be generated and source code will compile...
4. run several times `gradle test` (or in intellij)
5. see test report at build/reports/tests/test/index.html


Main goal of this project is to show some time-to-time to reproducible problems with ModelMapper and ProtoBuf generated projects.

So here in tests there are 2 cases

* TestMappingWithTrueValue tests are working time to time, either ok either converter does not work at all.
This is like bug that reproduces time to time.

* second test case TestMappingWithNULLValue which is ignored, because some kind of workaround need to be created
 pseudocode : `if  (source.getParent().hasSomeBoolValue()) { return value; } else { return null;}

* occasionally does not map value that starts with `W` like in test NotMappingValuesStartsWithW


