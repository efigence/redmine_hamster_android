package com.efigence.redhamster.domain.usecase;

public interface UseCase<Result, Argument> {

    Result execute(Argument arg);

}
