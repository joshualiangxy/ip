package main.parser;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import main.command.Command;
import main.command.AddDeadlineCommand;
import main.command.AddEventCommand;
import main.command.AddTodoCommand;
import main.command.DeleteCommand;
import main.command.DoneCommand;
import main.command.ExitCommand;
import main.command.ListCommand;
import main.exception.InvalidDateException;
import main.exception.InvalidDeadlineFormatException;
import main.exception.InvalidEventFormatException;
import main.exception.InvalidTaskException;
import main.exception.EmptyMessageException;
import main.exception.UnknownCommandException;

/**
 * Handles the parsing of user inputs.
 * @author Joshua Liang XingYa
 * @author joshualiang.xy@gmail.com
 * @version v0.1
 * @since v0.1
 */
public class Parser {
    private static final String EXIT_COMMAND = "bye";
    private static final String LIST_COMMAND = "list";
    private static final String DONE_COMMAND = "done";
    private static final String TODO_COMMAND = "todo";
    private static final String DEADLINE_COMMAND = "deadline";
    private static final String EVENT_COMMAND = "event";
    private static final String DELETE_COMMAND = "delete";

    private static LocalDateTime toDateTime(String dateTime) throws InvalidDateException {
        String[] dateTimeSplit = dateTime.split(" ");
        if (dateTimeSplit.length != 2)
            throw new InvalidDateException("     ☹ OOPS!!! Your date needs to"
                    + " have this format:\n     \"YYYY-MM-DD HHMM\"");

        String[] date = dateTimeSplit[0].split("-");
        String time = dateTimeSplit[1];

        if (date.length != 3)
            throw new InvalidDateException("     ☹ OOPS!!! Your date needs to"
                    + " have this format:\n     \"YYYY-MM-DD\"");
        if (time.length() != 4)
            throw new InvalidDateException("     ☹ OOPS!!! Your time needs to"
                    + " have this format:\n     \"HHMM\"");

        try {
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(2));

            return LocalDateTime.of(year, month, day, hour, minute);
        } catch (NumberFormatException | DateTimeException e) {
            throw new InvalidDateException("     ☹ OOPS!!! Please check that you've"
                    + " entered\n       the date and time correctly");
        }
    }

    private static Command parseAdd(String[] input)
            throws InvalidDeadlineFormatException,
                InvalidDateException,
                InvalidEventFormatException,
                UnknownCommandException {
        String command = input[0];
        String description = input[1];
        String[] nameAndTime;

        switch (command) {
        case TODO_COMMAND:
            return new AddTodoCommand(description);
        case DEADLINE_COMMAND:
            nameAndTime = description.split(" /by ", 2);
            if (nameAndTime.length == 1)
                throw new InvalidDeadlineFormatException();
            return new AddDeadlineCommand(nameAndTime[0], toDateTime(nameAndTime[1]));
        case EVENT_COMMAND:
            nameAndTime = description.split(" /at ", 2);
            if (nameAndTime.length == 1)
                throw new InvalidEventFormatException();
            return new AddEventCommand(nameAndTime[0], toDateTime(nameAndTime[1]));
        default:
            throw new UnknownCommandException();
        }
    }

    /**
     * Parses the input in the form of a string array and returns the
     * corresponding Command.
     * @param input the user input as a string array.
     * @return a Command based on the input.
     * @throws InvalidTaskException if the selected task does not exist.
     * @throws EmptyMessageException if the description of the task is empty.
     * @throws UnknownCommandException if the command given is unknown.
     * @throws InvalidDateException if the deadline or event dates are invalid.
     * @throws InvalidDeadlineFormatException if the format of deadline
     * command is invalid.
     * @throws InvalidEventFormatException if the format of event is invalid.
     */
    public static Command parse(String[] input)
            throws InvalidTaskException,
                EmptyMessageException,
                UnknownCommandException,
                InvalidDateException,
                InvalidDeadlineFormatException,
                InvalidEventFormatException {
        String command = input[0];
        boolean isSingleArgument = input.length == 1;
        int taskNum;

        switch (command) {
        case EXIT_COMMAND:
            return new ExitCommand();
        case LIST_COMMAND:
            return new ListCommand();
        case DONE_COMMAND:
            if (isSingleArgument) throw new InvalidTaskException();
            taskNum = Integer.parseInt(input[1]);
            return new DoneCommand(taskNum);
        case DELETE_COMMAND:
            if (isSingleArgument) throw new InvalidTaskException();
            taskNum = Integer.parseInt(input[1]);
            return new DeleteCommand(taskNum);
        case TODO_COMMAND:
        case DEADLINE_COMMAND:
        case EVENT_COMMAND:
            if (isSingleArgument) throw new EmptyMessageException(command);
            return parseAdd(input);
        default:
            throw new UnknownCommandException();
        }
    }
}
