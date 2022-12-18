<#macro addTransaction categories isIncome today>
    <form class="row g-3" action="/dashboard" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <div class="col-md-2 form-group">
            <label class="form-label" for="tSelect">Category</label>
            <select class="form-select" name="category" id="tSelect" required>
                <#list categories as category>
                    <option>${category.category}</option>
                </#list>
            </select>
        </div>

        <div class="col-md-2 form-group">
            <label class="form-label" for="tAmount">Amount</label>
            <div class="input-group ${((errorsMap['amountError'])??)?string('has-validation', '')}">
                <input class="form-control ${((errorsMap['amountError'])??)?string('is-invalid', '')}" type="number"
                       step="any" name="amount"
                       placeholder="Amount"
                       id="tAmount"
                       aria-describedby="basic-addon2"
                       value="<#if errorsMap?? && transactionDto?? && transactionDto.amount??>${(transactionDto.amount!"")?string.computer}</#if>">
                <span class="input-group-text"
                      id="basic-addon2">${0?string[",###¤;; currencyCode=${currentProfile.currency}"]?substring(1)}</span>
                <#if (errorsMap['amountError'])??>
                    <div class="invalid-feedback">
                        ${(errorsMap['amountError'])}
                    </div>
                </#if>
            </div>
        </div>

        <div class="col-md-2 form-group">
            <label class="form-label" for="tDate">Date</label>
            <input class="form-control ${((errorsMap['transactionDateError'])??)?string('is-invalid', '')}" type="date"
                   value="<#if errorsMap?? && transactionDto??>${transactionDto.transactionDate!""}<#else>${today}</#if>"
                   min="1970-01-01" name="transactionDate" id="tDate"
                   style="height: 2.4rem">
            <#if (errorsMap['transactionDateError'])??>
                <div class="invalid-feedback">
                    ${(errorsMap['transactionDateError'])}
                </div>
            </#if>
        </div>

        <div class="col-md-6 form-group">
            <label class="form-label" for="tMessage">Message</label>
            <input class="form-control ${((errorsMap['messageError'])??)?string('is-invalid', '')}" type="text"
                   name="message"
                   placeholder="Type message if you want" id="tMessage"
                   value="<#if errorsMap?? && transactionDto??>${transactionDto.message!""}</#if>">
            <#if (errorsMap['messageError'])??>
                <div class="invalid-feedback">
                    ${(errorsMap['messageError'])}
                </div>
            </#if>
        </div>

        <#if isIncome>
            <input type="hidden" name="isIncome" value="true">
        <#else>
            <input type="hidden" name="isIncome" value="false">
        </#if>

        <div class="col-12">
            <button type="submit" class="btn btn-success">Submit</button>
        </div>
    </form>
</#macro>