package structural.proxypattern;

//We cannot have direct access to this object, as any delete commands can be run by unauthorized persons.
//So we'll create proxy where we have some control.
public class CommandExecutorImpl implements CommandExecutor{

	@Override
	public void runCommand(String cmd) {
		System.out.println(cmd+" executed successfully");
	}

}
