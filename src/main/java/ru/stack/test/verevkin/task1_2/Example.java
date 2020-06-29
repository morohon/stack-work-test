package ru.stack.test.verevkin.task1_2;

import java.util.*;

/**
 * @author Kirill Verevkin
 */
class TestRunner {
    TestRunner(String name) {
        this.name = name;
    }

    interface BooleanTestCase {
        boolean run();
    }

    void expectTrue(BooleanTestCase cond) {
        try {
            if (cond.run()) {
                pass();
            } else {
                fail();
            }
        } catch (Exception e) {
            fail(e);
        }
    }

    void expectFalse(BooleanTestCase cond) {
        expectTrue(() -> !cond.run());
    }

    interface ThrowingTestCase {
        void run();
    }

    void expectException(ThrowingTestCase block) {
        try {
            block.run();
            fail();
        } catch (Exception e) {
            pass();
        }
    }

    private void fail() {
        System.out.printf("FAILED: Test %d of %s\n", testNo++, name);
    }

    private void fail(Exception e) {
        fail();
        e.printStackTrace(System.out);
    }

    private void pass() {
        System.out.printf("PASSED: Test %d of %s\n", testNo++, name);
    }

    private String name;
    private int testNo = 1;
}


class Matcher {

    public static final char CHAR_LATIN_SMALL_LETTER = 'a';
    public static final char CHAR_DIGIT = 'd';
    public static final char CHAR_WHITESPACE = ' ';
    public static final char CHAR_SMALL_LETTER_OR_DIGIT = '*';

    /**
     * Метод осуществляет проверку соответствия переданной строки шаблону.
     * Проверка осуществляется по количетсву символов (количество символов шаблона должно совпадать
     * с количеством символов сообщения), а также по типам символов описанным для параметра {@code pattern}
     * @param message
     *        Сообщение, которое необходимо проверить
     * @param pattern
     *        Шаблон проверки сообщения. Может содержать в себе следующие символы:
     *        <ul>
     *            <li><b>a</b> - строчная латинская буква (a-z)</li>
     *            <li><b>d</b> - цифра 0-9</li>
     *            <li><b>*</b> - строчная латинская буква (a-z) ИЛИ цифра 0-9</li>
     *            <li><b>' '</b> - пробел</li>
     *        </ul>
     * @throws IllegalArgumentException - передан не поддерживаемый шаблон сообщения.
     * @return {@code true} Если строка соответствует шаблону
     */
    static boolean match(String message, String pattern) {
        if (message.length() != pattern.length()){
            return false;
        }

        for (int i = 0; i < message.length(); i++) {
            switch (pattern.charAt(i)){
                case CHAR_LATIN_SMALL_LETTER:
                    //Здесь можно также пойти через порядковый номер символа
                    //Что-то вроде (int)message.charAt(i) < 97 || (int)message.charAt(i) > 122
                    if (!Character.toString(message.charAt(i)).matches("[a-z]")){
                        return false;
                    }
                    break;
                case CHAR_DIGIT:
                    if (!Character.isDigit(message.charAt(i))){
                        return false;
                    }
                    break;
                case CHAR_SMALL_LETTER_OR_DIGIT:
                    if (!Character.toString(message.charAt(i)).matches("[a-z\\d\\s]")){
                        return false;
                    }
                    break;
                case CHAR_WHITESPACE:
                    if (!Character.isSpaceChar(message.charAt(i))){
                        return false;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Указанный шаблон не поддерживается. Попробуйте указать другой.");
            }
        }

        return true;

    }

    static void testMatch() {
        TestRunner runner = new TestRunner("match");

        runner.expectFalse(() -> match("xy", "a"));
        runner.expectFalse(() -> match("x", "d"));
        runner.expectFalse(() -> match("0", "a"));
        runner.expectFalse(() -> match("*", " "));
        runner.expectFalse(() -> match(" ", "a"));

        runner.expectTrue(() -> match("01 xy", "dd aa"));
        runner.expectTrue(() -> match("1x", "**"));

        runner.expectException(() -> {
            match("x", "w");
        });
    }
}


class TaskFinder {
    static class Node {
        Node(int id, String name, Integer priority, List<Node> children) {
            this.id = id;
            this.name = name;
            this.priority = priority;
            this.children = children;
        }

        boolean isGroup() {
            return children != null;
        }

        int id;
        String name;
        Integer priority;
        List<Node> children;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return id == node.id
                    && name.equals(node.name)
                    && Objects.equals(priority, node.priority)
                    && Objects.equals(children, node.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, priority, children);
        }
    }

    static Node task(int id, String name, int priority) {
        return new Node(id, name, priority, null);
    }

    static Node group(int id, String name, Node... children) {
        return new Node(id, name, null, Arrays.asList(children));
    }


    static Node tasks =
            group(0, "Все задачи",
                    group(1, "Разработка",
                            task(2, "Планирование разработок", 1),
                            task(3, "Подготовка релиза", 4),
                            task(4, "Оптимизация", 2)),
                    group(5, "Тестирование",
                            group(6, "Ручное тестирование",
                                    task(7, "Составление тест-планов", 3),
                                    task(8, "Выполнение тестов", 6)),
                            group(9, "Автоматическое тестирование",
                                    task(10, "Составление тест-планов", 3),
                                    task(11, "Написание тестов", 3))),
                    group(12, "Аналитика"));


    static Optional<Node> findTaskHavingMaxPriorityInGroup(Node tasks, int groupId) {

        Node maxPriority;

        if (tasks.id == groupId){
            maxPriority = recursiveTree(tasks.children, null);
            return (maxPriority == null) ? Optional.empty() : Optional.of(maxPriority);
        } else {
            Node group = findGroupById(tasks.children, groupId);
            if (group == null){
                throw new IllegalArgumentException("Группа с таким идентификатором не найдена");
            }
            maxPriority = recursiveTree(group.children, null);
            return (maxPriority == null) ? Optional.empty() : Optional.of(maxPriority);
        }

    }

    private static Node findGroupById(List<Node> tasks, int groupId) {

        Node findGroup;

        for (Node group: tasks){
            if (!group.isGroup()) {
                continue;
            }

            if (group.id == groupId) {
                return group;
            } else {
                findGroup = findGroupById(group.children, groupId);
                if (findGroup != null){
                    return findGroup;
                }
            }
        }

        return null;

    }

    private static Node recursiveTree(List<Node> children, Node maxPriorityTask) {

        for (Node task : children){
            if (task.isGroup()){
                maxPriorityTask = recursiveTree(task.children, maxPriorityTask);
            } else {
                if (maxPriorityTask == null){
                    maxPriorityTask = task;
                    continue;
                }

                if (maxPriorityTask.priority < task.priority) {
                    maxPriorityTask = task;
                }
            }
        }

        return maxPriorityTask;

    }

    static void testFindTaskHavingMaxPriorityInGroup() {
        TestRunner runner = new TestRunner("findTaskHavingMaxPriorityInGroup");

        runner.expectException(() -> findTaskHavingMaxPriorityInGroup(tasks, 13));
        runner.expectException(() -> findTaskHavingMaxPriorityInGroup(tasks, 2));

        runner.expectFalse(() -> findTaskHavingMaxPriorityInGroup(tasks, 12).isPresent());

        runner.expectTrue(() -> findTaskHavingMaxPriorityInGroup(tasks, 0).get()
                .equals(task(8, "Выполнение тестов", 6)));
        runner.expectTrue(() -> findTaskHavingMaxPriorityInGroup(tasks, 1).get()
                .equals(task(3, "Подготовка релиза", 4)));

        runner.expectTrue(() -> findTaskHavingMaxPriorityInGroup(tasks, 9).get().priority == 3);
    }
}


class Main {
    public static void main(String args[]) {
        Matcher.testMatch();
        TaskFinder.testFindTaskHavingMaxPriorityInGroup();
    }
}
