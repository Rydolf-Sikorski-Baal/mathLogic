all:
	java mathLogic.target.classes.com.example.mathlogic.Tasks.TaskB

deploy:
	unzip task.zip

clean:
	rm -rf "target"
	rm -rf "target — копия"