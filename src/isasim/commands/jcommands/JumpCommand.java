package isasim.commands.jcommands;

import isasim.main.Processor;
import isasim.physical.Register;

/**
 * Created by ftoet on 26.11.2017.
 */
public class JumpCommand extends JCommand {
    @Override
    public String getName() {
        return "jump" + c.toString();
    }

    public JumpCommand(Register q, int offset, Condition c){
    super(q,offset,c) ;
    }

    @Override
    public void execute(Processor Main){
        if (Main.ConditionCheck(c)){
            Main.PC.setCount(this.basis_loaded + this.offset) ;
            Main.PipelineFlush();
        }
    }

}
