<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    currentUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = currentUser.getUsername()
    isAdmin = currentUser.isAdmin()
    >
<#else>
    <#assign
    name = "guest"
    isAdmin = false
    >
</#if>