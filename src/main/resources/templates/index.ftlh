<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <h2 class="mb-1">Hello, ${name}!</h2>
    <h4 class="mb-3">Here you can control your expenses.</h4>
    <#if name == "guest">
        <a href="/login" class="btn btn-success btn-sm">Login Page</a>
    <#else>
        <a href="/dashboard" class="btn btn-primary btn-sm">Go to dashboard</a>
    </#if>
</@c.page>