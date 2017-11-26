package isasim.commands.jcommands;

import isasim.main.Processor;
import isasim.physical.Register;

/**
 * Created by ftoet on 26.11.2017.
 */
public class JumpRCommand extends JCommand {
    @Override
    public String getName() {
        return "jump" + c.toString();
    }

    public JumpRCommand(Register q, int offset, Condition c){
    super(q,offset,c) ;
    }

    @Override
    public void execute(Processor Main){
        if (Main.ConditionCheck(c)){
            Main.PC.setCount(Main.PC.getCount() + this.basis_loaded + this.offset ) ;
        }
    }

}
