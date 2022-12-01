<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>List of transaction categories</h4>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>Category name</th>
            <th>Is income</th>
            <th>Save</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <#list categories as category>
            <tr class="table-<#if category.isIncome>success<#else>danger</#if>">
                    <td>${category.category}</td>
                    <td><input type="checkbox" name="isIncome" id="isIncomeCheck"
                               class="form-check-input" ${category.isIncome?string("checked", "")} disabled>
                    </td>
                    <td>
                        <a class="btn btn-outline-primary btn-sm" href="/categories/${category.id}"
                           role="button">Edit</a>
                    </td>
                <td>
                    <a class="btn btn-outline-danger btn-sm" href="/categories/delete/${category.id}"
                       role="button">Delete</a>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>