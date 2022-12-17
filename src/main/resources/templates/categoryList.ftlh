<#import "parts/common.ftlh" as c>

<@c.page>
    <h2>List of transaction categories</h2>

    <div class="col-md-10 mx-auto col-lg-8">
        <form action="/categories" method="post" class="m-4 row align-items-center p-4 border rounded-3 bg-light">
            <input type="hidden" value="${_csrf.token}" name="_csrf">

            <div class="mt-0 col">
                <label class="form-label" for="category">Name</label>
                <input class="form-control ${(categoryError??)?string('is-invalid', '')}" type="text" name="category"
                       placeholder="Category name"
                       id="category"
                       value="<#if categoryDto?? && categoryError??>${categoryDto.category!""}</#if>">
                <#if categoryError??>
                    <div class="invalid-feedback">
                        ${categoryError}
                    </div>
                </#if>
            </div>

            <div class="mt-0 col-2">
                <label for="isIncome" class="col-sm-4 form-label">Income</label>
                <div class="col-sm-8">
                    <input type="checkbox" id="isIncome" name="isIncome"
                           class="form-check-input">
                </div>
            </div>

            <div class="mt-0 col-4">
                <label class="col-sm-4 form-label" for="subm"></label>
                <input type="submit" class="btn btn-success mx-auto col-12" name="subbtn" value="Add category"
                       id="subm">
            </div>
        </form>
    </div>

    <div class="mb-4 table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Category name</th>
                <th style="text-align: center">Is income</th>
                <th style="text-align: center">Edit</th>
                <th style="text-align: center">Delete</th>
            </tr>
            </thead>

            <tbody>
            <#list categories as category>
                <tr>
                    <td>${category.category}</td>
                    <td class="table-<#if category.isIncome>success<#else>danger</#if>"
                        style="width: 10%; text-align: center">
                        <input type="checkbox" name="isIncome" id="isIncomeCheck"
                               class="form-check-input" ${category.isIncome?string("checked", "")} disabled>
                    </td>
                    <td style="width: 10%; text-align: center">
                        <a class="btn btn-outline-primary btn-sm" href="/categories/${category.id}"
                           role="button">Edit</a>
                    </td>
                    <td style="width: 10%; text-align: center">
                        <a class="btn btn-outline-danger btn-sm" href="/categories/delete/${category.id}"
                           role="button">Delete</a>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>