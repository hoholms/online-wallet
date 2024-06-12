<#include "security.ftlh">
<#import "auth.ftlh" as l>

<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="wallet" viewBox="0 0 16 16">
        viewBox="0 0 16 16">
        <title>Wallet</title>
        <path fill-rule="evenodd" clip-rule="evenodd"
              d="M12.136.326A1.5 1.5 0 0 1 14 1.78V3h.5A1.5 1.5 0 0 1 16 4.5v9a1.5 1.5 0 0 1-1.5 1.5h-13A1.5 1.5 0 0 1 0 13.5v-9a1.5 1.5 0 0 1 1.432-1.499L12.136.326zM5.562 3H13V1.78a.5.5 0 0 0-.621-.484L5.562 3zM1.5 4a.5.5 0 0 0-.5.5v9a.5.5 0 0 0 .5.5h13a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-13z"/>
    </symbol>
    <symbol id="statistics" viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M0 0h1v15h15v1H0V0Zm14.817 3.113a.5.5 0 0 1 .07.704l-4.5 5.5a.5.5 0 0 1-.74.037L7.06 6.767l-3.656 5.027a.5.5 0 0 1-.808-.588l4-5.5a.5.5 0 0 1 .758-.06l2.609 2.61 4.15-5.073a.5.5 0 0 1 .704-.07Z"/>
    </symbol>
    <symbol id="categories" viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M3 2v4.586l7 7L14.586 9l-7-7H3zM2 2a1 1 0 0 1 1-1h4.586a1 1 0 0 1 .707.293l7 7a1 1 0 0 1 0 1.414l-4.586 4.586a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 2 6.586V2z"/>
        <path fill-rule="evenodd"
              d="M5.5 5a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm0 1a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM1 7.086a1 1 0 0 0 .293.707L8.75 15.25l-.043.043a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 0 7.586V3a1 1 0 0 1 1-1v5.086z"/>
    </symbol>
    <symbol id="profile" viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
    </symbol>
    <symbol id="speedometer2" viewBox="0 0 16 16">
        <path d="M8 4a.5.5 0 0 1 .5.5V6a.5.5 0 0 1-1 0V4.5A.5.5 0 0 1 8 4zM3.732 5.732a.5.5 0 0 1 .707 0l.915.914a.5.5 0 1 1-.708.708l-.914-.915a.5.5 0 0 1 0-.707zM2 10a.5.5 0 0 1 .5-.5h1.586a.5.5 0 0 1 0 1H2.5A.5.5 0 0 1 2 10zm9.5 0a.5.5 0 0 1 .5-.5h1.5a.5.5 0 0 1 0 1H12a.5.5 0 0 1-.5-.5zm.754-4.246a.389.389 0 0 0-.527-.02L7.547 9.31a.91.91 0 1 0 1.302 1.258l3.434-4.297a.389.389 0 0 0-.029-.518z"/>
        <path fill-rule="evenodd"
              d="M0 10a8 8 0 1 1 15.547 2.661c-.442 1.253-1.845 1.602-2.932 1.25C11.309 13.488 9.475 13 8 13c-1.474 0-3.31.488-4.615.911-1.087.352-2.49.003-2.932-1.25A7.988 7.988 0 0 1 0 10zm8-7a7 7 0 0 0-6.603 9.329c.203.575.923.876 1.68.63C4.397 12.533 6.358 12 8 12s3.604.532 4.923.96c.757.245 1.477-.056 1.68-.631A7 7 0 0 0 8 3z"/>
    </symbol>
    <symbol id="people-circle" viewBox="0 0 16 16">
        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
        <path fill-rule="evenodd"
              d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
    </symbol>
</svg>

<nav class="bg-body col-md-sidebar mt-auto sidebar">
    <div class="position-sticky sidebar-sticky d-flex flex-sidebar flex-shrink-0 overflow-visible">
        <a href="/" class="d-block p-3 text-dark-emphasis text-decoration-none border-sidebar">
            <svg class="bi pe-none" width="40" height="32">
                <use xlink:href="#wallet"/>
            </svg>
            <span class="visually-hidden">Icon-only</span>
        </a>
        <ul class="nav nav-pills nav-flush flex-sidebar mb-auto text-center">
            <li>
                <a href="/dashboard"
                   class="nav-link <#if springMacroRequestContext.requestUri?contains("/dashboard")>active</#if> py-3 border-sidebar rounded-0"
                   title="Dashboard" data-bs-toggle="tooltip" data-bs-placement="right">
                    <svg class="bi pe-none" width="24" height="24" role="img" aria-label="Dashboard">
                        <use xlink:href="#speedometer2"/>
                    </svg>
                </a>
            </li>
            <li>
                <a href="/statistics"
                   class="nav-link <#if springMacroRequestContext.requestUri?contains("/statistics")>active</#if> py-3 border-sidebar rounded-0"
                   title="Statistics" data-bs-toggle="tooltip" data-bs-placement="right">
                    <svg class="bi pe-none" width="24" height="24" role="img" aria-label="Statistics">
                        <use xlink:href="#statistics"/>
                    </svg>
                </a>
            </li>
            <#if isAdmin>
                <li>
                    <a href="/users"
                       class="nav-link <#if springMacroRequestContext.requestUri?contains("/users")>active</#if> py-3 border-sidebar rounded-0"
                       title="User list" data-bs-toggle="tooltip" data-bs-placement="right">
                        <svg class="bi pe-none" width="24" height="24" role="img" aria-label="User list">
                            <use xlink:href="#people-circle"/>
                        </svg>
                    </a>
                </li>
                <li>
                    <a href="/categories"
                       class="nav-link <#if springMacroRequestContext.requestUri?contains("/categories")>active</#if> py-3 border-sidebar rounded-0"
                       title="Categories" data-bs-toggle="tooltip" data-bs-placement="right">
                        <svg class="bi pe-none" width="24" height="24" role="img" aria-label="Categories">
                            <use xlink:href="#categories"/>
                        </svg>
                    </a>
                </li>
            </#if>
        </ul>
        <#if currentUser??>
            <div class="dropup border-profile">
                <a href="#"
                   class="nav-link d-flex align-items-center justify-content-center p-3 text-decoration-none dropdown-toggle"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    <svg class="bi pe-none" width="24" height="24" role="img" aria-label="Statistics">
                        <use xlink:href="#profile"/>
                    </svg>
                </a>
                <ul class="dropdown-menu text-small mb-sm-1 ms-sm-1">
                    <li><a class="dropdown-item" href="/profile">Profile</a></li>
                    <li><a class="dropdown-item" href="https://github.com/hoholms/online-wallet" target="_blank">GitHub
                            Repo</a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li>
                        <form action="/logout" method="post">
                            <#--noinspection FtlReferencesInspection-->
                            <input type="hidden" name="_csrf" value="${_csrf.token}">
                            <button type="submit" class="dropdown-item dropdown-item-danger">Sign Out</button>
                        </form>
                    </li>
                </ul>
            </div>
        </#if>
    </div>
</nav>
