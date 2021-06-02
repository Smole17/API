package gloomyfolken.hooklib.asm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Чтобы сделать метод хуком, нужно повесить над ним эту аннотацию и зарегистрировать класс с хуком.
 *
 * Целевой класс определяется первым параметром хук-метода. Если целевой метод static, то туда прилетает null,
 * иначе - this.
 *
 * Название целевого метода по умолчанию такое же, как название хук-метода, но его можно переопределить через
 * targetMethod.
 *
 * Список параметров целевого метода определяется списком параметров хук-метода. Нужно добавить все те же параметры
 * в том же порядке.
 *
 * Возвращаемый тип целевого метода по умолчанию не указывается вообще. Предполагается, что методов с одинаковым
 * названием и списком параметров нет. Если всё же нужно указать, то это можно сделать через returnType.
 *
 */
@Target(ElementType.METHOD)
public @interface Hook {

    /**
     * Задает условие, по которому после вызова хука будет вызван return.
     * Если целевой метод возвращает не void, то по умолчанию будет возвращено то, что вернул хук-метод.
     * Это можно переопредилить несколькими элементами аннотации:
     * returnAnotherMethod, returnNull и $type$ReturnConstant.
     */
    ReturnCondition returnCondition() default ReturnCondition.NEVER;

    /**
     * Задает приоритет хука.
     * Хуки с большим приоритетом вызаваются раньше.
     */
    HookPriority priority() default HookPriority.NORMAL;

    /**
     * Задает название целевого метода.
     * По умолчанию используется название хук-метода.
     * Эта опция полезна, когда нужно вставить хук в конструктор или инициализацию класса.
     * Для конструктора targetMethod должен быть "<init>", для инициализации класса - "<clinit>"
     */
    String targetMethod() default "";

    /**
     * Задает тип, возвращаемый целевым методом.
     * С точки зрения JVM могут быть методы, которые отличаются только возращаемым типом.
     * На практике компилятор таких методов не генерируют, но в некоторых случаях они
     * могут встретиться (например, это можно сделать при обфускации через ProGuard)
     * Если возвращаемый тип не указан, то хук применяется к первому методу, подходящему
     * по названию и списку параметров.
     */
    String returnType() default "";

    /**
     * По умолчанию хук вставляется в начало целевого метода.
     * Если указать здесь true, то он будет вставлен в конце и перед каждым вызовом return.
     */
    boolean injectOnExit() default false;

    /**
     * По умолчанию хук вставляется в начало целевого метода.
     * Если указать здесь true, то он будет вставлен в начале указанной строки.
     * Использовать не рекомендуется, потому что:
     * 1) Вставить можно только на строку с инструкцией
     * 2) Может ВНЕЗАПНО сломаться (например, от того, что какой-нибудь оптифайн подменит класс целиком)
     */
    @Deprecated int injectOnLine() default -1;

    /**
     * Если указано это название, то при вызове return в целевом методе будет сначала вызван этот метод.
     * Он должен находиться в том же классе и иметь тот же список параметров, что и хук-метод.
     * В итоге будет возвращено значение, которое вернёт этот метод.
     */
    String returnAnotherMethod() default "";

    /**
     * Если true, то при вызове return в целевом методе будет возвращено null
     */
    boolean returnNull() default false;

    /**
     * Если определена одна из этих констант, то она будет возвращена при вызове return в целевом методе
     */

    byte byteReturnConstant() default 0;

    short shortReturnConstant() default 0;

    int intReturnConstant() default 0;

    long longReturnConstant() default 0L;

    float floatReturnConstant() default 0.0F;

    double doubleReturnConstant() default 0.0D;

    char charReturnConstant() default 0;

    String stringReturnConstant() default "";

    @Target(ElementType.PARAMETER)
    @interface LocalVariable {
        int value();
    }

    /**
     * Перехватывает значение, которое изначально шло в return, и передает его хук-методу.
     * Говоря более формально, передает последнее значение в стаке.
     * Можно использовать только когда injectOnExit() == true и целевой метод возвращает не void.
     */
    @Target(ElementType.PARAMETER)
    @interface ReturnValue {}
}
