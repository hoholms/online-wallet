<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>
    <div>
        <ul class="pagination justify-content-center">
            <li class="page-item <#if page.getNumber() == 0>disabled</#if>">
                <div class="dropdown mt-0">
                    <button class="page-link dropdown-toggle" type="button" id="dropdownMenuButton"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        Page Size: ${page.getSize()}
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <#list [5, 10, 25, 50] as c>
                            <#if c == page.getSize()>
                                <li>
                                    <a class="dropdown-item active" href="#" tabindex="-1">${c}</a>
                                </li>
                            <#else>
                                <li>
                                    <a class="dropdown-item"
                                       href="${url}?page=<#if page.getNumber() gt page.getTotalElements() / c>${(page.getTotalElements() / c)?round - 1}<#else>${page.getNumber()}</#if>&size=${c}"
                                       tabindex="-1">${c}</a>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </li>
            <li class="page-item <#if page.getNumber() == 0>disabled</#if>">
                <a class="page-link" href="${url}?page=${page.getNumber() - 1}&size=${page.getSize()}" tabindex="-1">Previous</a>
            </li>
            <#list body as p>
                <#if (p - 1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#elseif p == -1>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${p - 1}&size=${page.getSize()}" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
            <li class="page-item <#if page.getNumber() == page.getTotalPages() - 1>disabled</#if>">
                <a class="page-link" href="${url}?page=${page.getNumber() + 1}&size=${page.getSize()}" tabindex="-1">Next</a>
            </li>
        </ul>
    </div>
</#macro>
