# Contributing to kotlin-head

## How to contribute

1. **Fork** the repository
2. **Create a branch** (`git checkout -b feature/your-feature`)
3. **Make your changes** — keep them focused and small
4. **Test** — run `kotlinc -d kotlin-head.jar $(find src -name '*.kt')` to ensure zero errors, and test against hell_v4.kt
5. **Commit** with a clear message
6. **Push** and open a **Pull Request**

## Code standards

- No dependencies beyond Kotlin stdlib
- Parser errors must produce diagnostics — never fail silently
- New features should add a test case in hell_v4.kt or a new hell file
- Follow existing code style (2-space indent, Kotlin idioms)

## Reporting bugs

Use GitHub Issues. Include:
- The input file that triggers the bug
- Expected vs actual behavior
- kotlin-head version

## License

By contributing, you agree that your contributions will be licensed under the AGPL-3.0 license.
