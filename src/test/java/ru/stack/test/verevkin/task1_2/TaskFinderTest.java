package ru.stack.test.verevkin.task1_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kirill Verevkin
 */
class TaskFinderTest {

    @Test
    void findTaskHavingMaxPriorityInGroup() {

        assertThrows(IllegalArgumentException.class,() -> TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 13));
        assertThrows(IllegalArgumentException.class,() -> TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 2));

        assertFalse(() -> TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 12).isPresent());

        assertEquals(TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 0).get(),
                TaskFinder.task(8, "Выполнение тестов", 6));
        assertEquals(TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 1).get(),
                TaskFinder.task(3, "Подготовка релиза", 4));

        assertEquals(TaskFinder.findTaskHavingMaxPriorityInGroup(TaskFinder.tasks, 9).get().priority, 3);

    }
}