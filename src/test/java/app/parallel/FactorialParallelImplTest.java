package app.parallel;

import app.FactorialTest;

public class FactorialParallelImplTest extends FactorialTest {

    public FactorialParallelImplTest() {
        super(new FactorialParallelImpl(10));
    }
}