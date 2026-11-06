package com.diconium.mobile.tools.kebabkrafter

fun warningKtorBinaryType(name: String, isClient: Boolean = true) =
    "/!\\ Generate ${if (isClient) "Client" else "Server"} - " +
        "Ktor binary support is not enabled for `$name`." + System.lineSeparator() +
        "Generated clients will use the deprecated `InputStream` type, " +
        "which will be replaced by `ByteReadChannel` in version 3.0. Enable `optInKtorBinaryType` to migrate."
