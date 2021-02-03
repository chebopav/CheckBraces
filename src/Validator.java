public class Validator {
    private final String figure = "{";
    private final String round = "(";
    private final String square = "[";

    /**
     * Основной метод проверки правильности закрытия всех скобок
     * При отсутствии скобок - результат true
     * При наличии проверка на соответствие открывающихся и закрывающихся скобок
     * Затем идет проверка последней пары скобок и рекурсивный вызов метода для части строки
     * в последней пары скобок и для оставшейся части
     * @param inputString строка для проверки
     * @return результат проверки на правильность расстановки скобок
     */
    public boolean checkBraces(String inputString) {

        // Если нет скобок - результат true
        if (hasNoBracers(inputString))
            return true;

        // проверка на соответствие открывающихся и закрывающихся скобок
        if (!checkAllBracesOpenAndClose(inputString))
            return false;

        // проверяем какой вид открывающейся скобки последний
        String openChar = null;
        if (inputString.lastIndexOf(figure) > inputString.lastIndexOf(round)
                && inputString.lastIndexOf(figure) > inputString.lastIndexOf(square)) {
            openChar = figure;
        }
        if (inputString.lastIndexOf(round) > inputString.lastIndexOf(figure)
                && inputString.lastIndexOf(round) > inputString.lastIndexOf(square)) {
            openChar = round;
        }
        if (inputString.lastIndexOf(square) > inputString.lastIndexOf(figure)
                && inputString.lastIndexOf(square) > inputString.lastIndexOf(round)) {
            openChar = square;
        }

        // обрезаем строку по скобкам, после чего будем проверять часть внутри скобок и часть вне скобок
        int indexOfOpen = inputString.lastIndexOf(openChar);
        String firstPart = inputString.substring(0, indexOfOpen);
        String inBracers = inputString.substring(inputString.lastIndexOf(openChar));
        int indexOfClosing = inBracers.indexOf(getClosingChar(openChar));
        inBracers = inBracers.substring(1, indexOfClosing);
        String thirdPart = inputString.substring(indexOfOpen + indexOfClosing + 1);
        String withoutBraces = firstPart + thirdPart;
        return checkBraces(withoutBraces) && checkBraces(inBracers);
    }

    /**
     * Вспомогательный метод для получения закрывающейся скобки по открывающейся
     * @param openChar открывающаяся скобка
     * @return закрывающаяся скобка
     */
    private String getClosingChar(String openChar) {
        switch (openChar) {
            case "{":
                return "}";
            case "(":
                return ")";
            case "[":
                return "]";
            default:
                throw new IllegalArgumentException("Не возможно подобрать закрывающую скобку");
        }
    }

    /**
     * Проверка наличия пары открывающаяся-закрывающаяся скобка
     * @param inputString строка для проверки
     * @param ch открывающаяся скобка
     * @return результат проверки
     */
    private boolean checkOpenAndClose(String inputString, String ch) {
        if (inputString.contains(ch)) {
            return inputString.contains(getClosingChar(ch)) && inputString.lastIndexOf(getClosingChar(ch)) > inputString.lastIndexOf(ch);
        } else if (inputString.contains(getClosingChar(ch))) {
            return inputString.contains(ch);
        }
        return true;
    }

    /**
     * Проверка наличия закрывающихся скобок для каждого вида ("{", "[", "(")
     * @param inputString строка для проверки
     * @return результат проверки
     */
    private boolean checkAllBracesOpenAndClose(String inputString){
        if (!checkOpenAndClose(inputString, figure)) {
            return false;
        }
        if (!checkOpenAndClose(inputString, round)) {
            return false;
        }
        if (!checkOpenAndClose(inputString, square)) {
            return false;
        }
        return true;
    }

    /**
     * Проверка на отсутствие скобок в строке
     * @param inputString строка для проверки
     * @return результат проверки
     */
    private boolean hasNoBracers(String inputString) {
        return !inputString.contains(figure) && !inputString.contains(getClosingChar(figure)) &&
                !inputString.contains(round) && !inputString.contains(getClosingChar(round)) &&
                !inputString.contains(square) && !inputString.contains(getClosingChar(square));
    }

}


