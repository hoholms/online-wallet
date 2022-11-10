<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">

<@c.page>
    <h5>Hello, ${name}!</h5>
    <div class="mb-1">Here you can control your expenses.</div>
    <#if name == "guest">
        <a href="/login" class="btn btn-success btn-sm">Login Page</a>
    </#if>
</@c.page>