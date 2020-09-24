package com.br.workdate

import com.br.workdate.database.converter.ConvertersTest
import com.br.workdate.extension.BigDecimalExtensionTest
import com.br.workdate.extension.StringExtensionTest
import com.br.workdate.model.QueryCreatorFilterTest
import com.br.workdate.model.ResumeReleaseTest
import com.br.workdate.model.ResumeScheduleTest
import com.br.workdate.view.recyclerview.adapter.ClientAdapterTest
import com.br.workdate.view.recyclerview.adapter.ScheduleAdapterTest
import com.br.workdate.view.recyclerview.adapter.ServiceAdapterTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ConvertersTest::class,
    BigDecimalExtensionTest::class,
    StringExtensionTest::class,
    QueryCreatorFilterTest::class,
    ResumeReleaseTest::class,
    ResumeScheduleTest::class,
    ClientAdapterTest::class,
    ScheduleAdapterTest::class,
    ServiceAdapterTest::class,
    ResumeReleaseTest::class
)
class UnitTestSuite