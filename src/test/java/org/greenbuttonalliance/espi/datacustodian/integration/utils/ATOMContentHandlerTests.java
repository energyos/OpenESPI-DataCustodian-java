/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.datacustodian.integration.utils;

import org.greenbuttonalliance.espi.common.service.EntryProcessorService;
import org.greenbuttonalliance.espi.common.utils.ATOMContentHandler;
import org.greenbuttonalliance.espi.datacustodian.utils.factories.FixtureFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.SAXParserFactory;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class ATOMContentHandlerTests {
	@Autowired
	@Qualifier("domainMarshaller")
	private Jaxb2Marshaller jaxb2Marshaller;

	@Autowired
	private EntryProcessorService entryProcessorService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
//	@Ignore
	public void processEnty() throws Exception {
		JAXBContext context = jaxb2Marshaller.getJaxbContext();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XMLReader reader = factory.newSAXParser().getXMLReader();
		// EntryProcessorServiceImpl procssor =
		// mock(EntryProcessorServiceImpl.class);
		ATOMContentHandler atomContentHandler = new ATOMContentHandler(context,
				entryProcessorService);

		reader.setContentHandler(atomContentHandler);

		reader.parse(new InputSource(FixtureFactory
				.newUsagePointInputStream(UUID.randomUUID())));

		// verify(procssor).process(any(EntryType.class));
	}
}
