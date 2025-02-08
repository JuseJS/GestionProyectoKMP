package domain.common

abstract class UseCase<in P, R> {
    abstract suspend operator fun invoke(parameters: P): Result<R>
}