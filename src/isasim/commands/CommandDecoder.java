package isasim.commands;

import isasim.commands.icommands.*;
import isasim.commands.jcommands.Condition;
import isasim.commands.jcommands.JCommand;
import isasim.commands.jcommands.JumpCommand;
import isasim.commands.jcommands.JumpRCommand;
import isasim.commands.rcommands.*;
import isasim.main.Processor;
import isasim.physical.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ftoet on 26.11.2017.
 */
public final class CommandDecoder {

    private static final Map<Integer, Class<? extends RCommand>> AluRFunctionMap;

    static {
        Map<Integer, Class<? extends RCommand>> aMap = new HashMap<Integer, Class<? extends RCommand>>();
        aMap.put(1, AddCommand.class);
        aMap.put(2, SubCommand.class);
        aMap.put(3, AndCommand.class);
        aMap.put(4, OrCommand.class);
        aMap.put(5, XOrCommand.class);
        aMap.put(6, SHLCommand.class);
        aMap.put(7, SHRCommand.class);
        aMap.put(8, SHARCommand.class);
        AluRFunctionMap = Collections.unmodifiableMap(aMap);
    }

    private static final Map<Integer, Class<? extends ICommand>> AluIFunctionMap;

    static {
        Map<Integer, Class<? extends ICommand>> aMap = new HashMap<Integer, Class<? extends ICommand>>();
        aMap.put(1, AddICommand.class);
        aMap.put(2, SubICommand.class);
        aMap.put(3, AndICommand.class);
        aMap.put(4, OrICommand.class);
        aMap.put(5, XOrICommand.class);
        aMap.put(6, SHLICommand.class);
        aMap.put(7, SHRICommand.class);
        aMap.put(8, SHARICommand.class);
        AluIFunctionMap = Collections.unmodifiableMap(aMap);
    }


    public static Command decodeCommand(int Code, Processor p) {
        int Opcode;
        Opcode = Code >>> 26;
        int CommandType = Opcode >>> 4;
        int RegisterNumber1;
        int RegisterNumber2;
        int RegisterNumber3;
        int AluFunction;
        int opCode_Without_Command_Type;
        Class[] cArg;
        opCode_Without_Command_Type = (Code << 2) >>> 28;
        switch (CommandType) {
            case 0: //R Format
                //Get the three Register Numbers
                RegisterNumber1 = (Code << 6) >>> 27;
                RegisterNumber2 = (Code << 11) >>> 27;
                RegisterNumber3 = (Code << 16) >>> 27;
                AluFunction = (Code << 21) >>> 28;
                //opCode_Without_Command_Type = (Opcode << 4) >>> 4;


                switch (opCode_Without_Command_Type) {
                    case 0: //halt Command
                        //TODO
                        break;
                    case 1:// mov
                        return new MoveCommand(p.Registerbank.get(RegisterNumber1), p.Registerbank.get(RegisterNumber2), p.Registerbank.get(RegisterNumber3));
                    case 2: // alu without setFlags
                        cArg = new Class[4]; //Our constructor has 3 arguments
                        cArg[0] = Register.class; //First argument is of *object* type Long
                        cArg[1] = Register.class; //Second argument is of *object* type String
                        cArg[2] = Register.class; //Third argument is of *primitive* type int
                        cArg[3] = Boolean.class;
                        if(AluRFunctionMap.get(AluFunction) == null){
                            return null ;
                        }
                        try {
                            return AluRFunctionMap.get(AluFunction).getDeclaredConstructor(cArg).newInstance(p.Registerbank.get(RegisterNumber1), p.Registerbank.get(RegisterNumber2), p.Registerbank.get(RegisterNumber3), false);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3: // alu with setFlags
                        cArg = new Class[4];
                        cArg[0] = Register.class;
                        cArg[1] = Register.class;
                        cArg[2] = Register.class;
                        cArg[3] = Boolean.class;
                        if(AluRFunctionMap.get(AluFunction) == null){
                            return null ;
                        }
                        try { // get the corresponding Class from the Map.
                            return AluRFunctionMap.get(AluFunction).getDeclaredConstructor(cArg).newInstance(p.Registerbank.get(RegisterNumber1), p.Registerbank.get(RegisterNumber2), p.Registerbank.get(RegisterNumber3), true);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
            case 1: //I Format
                //Get the three Register Numbers
                RegisterNumber1 = (Code << 6) >>> 27;
                RegisterNumber2 = (Code << 11) >>> 27;
                AluFunction = (Code << 16) >>> 28;
                int Immediate = (Code << 20) >>> 20;

                System.out.println("OpcodeWithout: " + opCode_Without_Command_Type) ;
                switch (opCode_Without_Command_Type) {
                    case 0: //movi Command
                        return new MoveICommand(p.Registerbank.get(RegisterNumber1), Immediate, p.Registerbank.get(RegisterNumber2));
                    case 1:// alu without setFlags
                        cArg = new Class[4];
                        cArg[0] = Register.class;
                        cArg[1] = int.class;
                        cArg[2] = Register.class;
                        cArg[3] = Boolean.class;
                        if(AluIFunctionMap.get(AluFunction) == null){
                            return null ;
                        }
                        try { // get the corresponding Class from the Map.
                            return AluIFunctionMap.get(AluFunction).getDeclaredConstructor(cArg).newInstance(p.Registerbank.get(RegisterNumber1), Immediate, p.Registerbank.get(RegisterNumber2), false);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2: // alu with setFlags
                        cArg = new Class[4];
                        cArg[0] = Register.class;
                        cArg[1] = int.class;
                        cArg[2] = Register.class;
                        cArg[3] = Boolean.class;
                        if(AluIFunctionMap.get(AluFunction) == null){
                            return null ;
                        }
                        try { // get the corresponding Class from the Map.
                            return AluIFunctionMap.get(AluFunction).getDeclaredConstructor(cArg).newInstance(p.Registerbank.get(RegisterNumber1), Immediate, p.Registerbank.get(RegisterNumber2), true);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 3: // load
                        return new LoadCommand(p.Registerbank.get(RegisterNumber1), Immediate, p.Registerbank.get(RegisterNumber2));
                    case 4: // store
                        return new StoreCommand(p.Registerbank.get(RegisterNumber1), Immediate, p.Registerbank.get(RegisterNumber2));
                }
                break;
            case 2:  //J Format
                Condition condtion = Condition.getConditionFromCode((Code << 6) >>> 28);
                RegisterNumber1 = (Code << 10) >>> 27;
                int offset = (Code << 15) >>> 15;
                if (condtion == null){
                    return null ;
                }
                switch (opCode_Without_Command_Type) {
                    case 0: //relative Sprung
                        return new JumpRCommand(p.Registerbank.get(RegisterNumber1),offset,condtion) ;
                    case 1: //absolut Sprung
                        return new JumpCommand(p.Registerbank.get(RegisterNumber1),offset,condtion) ;
                }
        }
    return  null ;
    }
}
