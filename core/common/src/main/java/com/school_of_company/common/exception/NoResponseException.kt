package com.school_of_company.common.exception

import java.io.IOException

class NoResponseException(
    override val message: String = "주소 검색에 대한 응답이 없습니다."
) : IOException()