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
                <form>
                    <input type="hidden" name="categoryId" value="${category.id}">
                    <td><input type="text" name="categoryName" value="${category.category}" class="form-control form-control-sm"></td>
                    <td><input type="checkbox" name="categoryIsIncome" id="isIncomeCheck"
                               class="form-check-input" ${category.isIncome?string("checked", "")}>
                    </td>
                    <td><button class="btn btn-primary btn-sm" type="submit">Save</button></td>
                </form>
                    <td>Delete</td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>