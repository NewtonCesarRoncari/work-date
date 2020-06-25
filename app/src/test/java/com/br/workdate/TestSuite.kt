package com.br.workdate

import com.br.workdate.database.converter.ConvertersTest
import com.br.workdate.extension.BigDecimalExtensionTest
import com.br.workdate.extension.StringExtensionTest
import com.br.workdate.model.ReleaseFilterTest
import com.br.workdate.model.ResumeTest
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
    ReleaseFilterTest::class,
    ResumeTest::class,
    ClientAdapterTest::class,
    ScheduleAdapterTest::class,
    ServiceAdapterTest::class
)
class TestSuite