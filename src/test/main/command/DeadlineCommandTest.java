package main.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import main.exception.InvalidOptionException;
import main.task.Deadline;
import main.task.Event;
import main.task.TaskList;
import main.task.Todo;
import main.ui.Ui;

public class DeadlineCommandTest {
    private static final Ui UI = new Ui();
    private static TaskList tasks;

    @BeforeEach
    public void beforeEach() throws InvalidOptionException {
        Todo taskOne = new Todo("task 1", true);
        Deadline taskTwo = new Deadline("task 2",
                LocalDateTime.of(193, 7, 26, 13, 50), new HashSet<>());
        Event taskThree = new Event(
                "task 3", "", "1993-12-06T10:10", false);
        tasks = new TaskList();
        tasks.add(taskOne);
        tasks.add(taskTwo);
        tasks.add(taskThree);
    }

    @Nested
    @DisplayName("execute")
    class Execute {
        @Test
        @DisplayName("should add task to list and return add success string")
        public void execute_deadlineTask_addSuccess() {
            String name = "test";
            LocalDateTime time = LocalDateTime.of(1032, 5, 2, 14, 12);
            DeadlineCommand command = new DeadlineCommand(name, time, new HashSet<>());
            Deadline task = new Deadline(name, time, new HashSet<>());

            assertEquals(3, tasks.size());
            assertEquals(UI.printAddSuccess(task, 4), command.execute(UI, tasks));
            assertEquals(4, tasks.size());
            assertEquals(task, tasks.get(3));
        }

        @Test
        @DisplayName("should add alternate task to list and return add success string")
        public void execute_altDeadlineTask_addSuccess() {
            String name = "test 2";
            LocalDateTime time = LocalDateTime.of(9032, 9, 22, 19, 42);
            DeadlineCommand command = new DeadlineCommand(name, time, new HashSet<>());
            Deadline task = new Deadline(name, time, new HashSet<>());

            assertEquals(3, tasks.size());
            assertEquals(UI.printAddSuccess(task, 4), command.execute(UI, tasks));
            assertEquals(4, tasks.size());
            assertEquals(task, tasks.get(3));
        }
    }

    @Nested
    @DisplayName("has command after")
    class HasCommandAfter {
        @Test
        @DisplayName("should return true")
        public void hasCommandAfter_noInput_true() {
            System.out.println("here");
            assertTrue(new DeadlineCommand(
                    "test",
                    LocalDateTime.of(1992, 10, 3, 19, 2),
                    new HashSet<>()
                ).hasCommandAfter());
        }
    }
}
