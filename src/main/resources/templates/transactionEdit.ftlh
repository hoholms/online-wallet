<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="row align-items-center">
        <div class="col-md-10 mx-auto col-lg-8">
            <form action="/transactions" method="post" class="p-4 border rounded-3 bg-light-subtle">
                <h1 class="h2 mb-3 fw-normal text-center">Transaction editor</h1>
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <input type="hidden" value="${id}" name="id">

                <div class="form-floating mb-3">
                    <select class="form-select ${(categoryError??)?string('is-invalid', '')}" name="category"
                            id="transactionId">
                        <#list categories as category>
                            <#if category == transactionEdit.category || (category.category == transactionEdit.category)>
                                <option selected="selected">${category.category}</option>
                            <#else>
                                <option>${category.category}</option>
                            </#if>
                        </#list>
                    </select>
                    <label for="transactionId" class="col-sm-2 col-form-label">Category</label>
                    <#if categoryError??>
                        <div class="invalid-feedback">
                            ${categoryError}
                        </div>
                    </#if>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" id="message" name="message"
                           class="form-control ${(messageError??)?string('is-invalid', '')}"
                           value="${transactionEdit.message!""}"
                           placeholder="Message">
                    <label for="message" class="col-sm-2 col-form-label">Message</label>
                    <#if messageError??>
                        <div class="invalid-feedback">
                            ${messageError}
                        </div>
                    </#if>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" id="tAmount" name="amount"
                           class="form-control ${(amountError??)?string('is-invalid', '')}"
                           placeholder="Amount"
                           value="${(transactionEdit.amount?string.computer)!""}">
                    <label for="tAmount" class="col-sm-2 col-form-label">Amount</label>
                    <#if amountError??>
                        <div class="invalid-feedback">
                            ${amountError}
                        </div>
                    </#if>
                </div>

                <div class="form-floating mb-3">
                    <input type="date" id="tDate" name="transactionDate"
                           class="form-control ${(transactionDateError??)?string('is-invalid', '')}" placeholder="Date"
                           min="1970-01-01" value="${transactionEdit.transactionDate}">
                    <label for="tDate" class="col-sm-2 col-form-label">Date</label>
                    <#if transactionDateError??>
                        <div class="invalid-feedback">
                            ${transactionDateError}
                        </div>
                    </#if>
                </div>

                <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Save</button>
                <a href="/transactions/delete/${id}" class="w-100 btn btn-lg btn-outline-danger">Delete</a>
            </form>
        </div>
    </div>
</@c.page>