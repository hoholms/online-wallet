<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>

<@c.page>
    <h2>List of transaction categories</h2>

    <div class="mx-auto col-md-10 col-lg-8 overflow-auto">
        <form action="/categories" method="post"
              class="my-4 mx-0 row align-items-center p-4 border rounded-3 bg-light-subtle overflow-auto">
            <input type="hidden" value="${_csrf.token}" name="_csrf">

            <div class="mt-0 col-sm">
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

            <div class="mt-0 col-sm-2">
                <label for="isIncome" class="col-sm-4 form-label">Income</label>
                <div class="col-sm-8">
                    <input type="checkbox" id="isIncome" name="isIncome"
                           class="form-check-input">
                </div>
            </div>

            <div class="mt-0 col-sm-4">
                <label class="col-sm-4 form-label" for="subm"></label>
                <input type="submit" class="btn btn-success mx-auto col-12" name="subbtn" value="Add category"
                       id="subm">
            </div>
        </form>
    </div>

    <div class="my-4 row">
        <div class="col-md table-responsive">
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
                <#list incomeCategories.content as category>
                    <tr>
                        <td>${category.category}</td>
                        <td
                                style="width: 15%; text-align: center;
                                        background-color: <#if category.isIncome>var(--bs-success-border-subtle)<#else>var(--bs-danger-border-subtle)</#if>">
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
        <div class="col-md table-responsive">
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
                <#list expenseCategories.content as category>
                    <tr>
                        <td>${category.category}</td>
                        <td
                                style="width: 15%; text-align: center;
                                        background-color: <#if category.isIncome>var(--bs-success-border-subtle)<#else>var(--bs-danger-border-subtle)</#if>">
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
    </div>
    <@p.pager "/categories" categories/>
</@c.page>