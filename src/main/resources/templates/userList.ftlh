<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>List of users</h4>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Edit</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.authority as authority>${authority}<#sep>, </#list></td>
                <td><a href="/user/${user.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>