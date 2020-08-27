package main;

import java.io.IOException;
import java.util.Scanner;

import main.command.Command;
import main.exception.DukeException;
import main.parser.Parser;
import main.storage.Storage;
import main.task.TaskList;
import main.ui.Ui;

/**
 * Duke application.
 * @author Joshua Liang XingYa
 * @author joshualiang.xy@gmail.com
 * @version v0.1
 * @since v0.1
 */
public class Duke {
    private final Ui ui;
    private final Scanner sc;
    private final TaskList tasks;
    private boolean hasCommand;

    /**
     * Constructs the duke application.
     */
    public Duke() {
        sc = new Scanner(System.in);
        hasCommand = false;
        tasks = new TaskList();
        ui = new Ui();
    }

    /**
     * Initialises the duke application.
     */
    public void initialise() {
        try {
            hasCommand = true;
            ui.printGreeting();
            Storage.setTasks(tasks);

            while (hasCommand) {
                try {
                    String[] input = ui.getInput(sc);
                    Command command = Parser.parse(input);
                    command.execute(ui, tasks);

                    hasCommand = command.hasCommand();
                } catch (DukeException e) {
                    ui.printExceptionMessage(e);
                }
            }

            Storage.write(tasks);
        } catch (IOException e) {
            ui.printError();
        } finally {
            ui.printExit();
        }
    }

    public static void main(String[] args) {
        new Duke().initialise();
    }
}
