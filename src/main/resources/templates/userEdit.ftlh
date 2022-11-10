<#import "parts/common.ftlh" as c>

<@c.page>
    User editor

    <form action="/user" method="post">
        <input type="text" name="username" value="${user.username}">
        <#list authorities as authority>
            <div>
                <label> <input type="checkbox" name="${authority}" ${user.authorities?seq_contains(authority)?string("checked", "")}>${authority}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</@c.page>