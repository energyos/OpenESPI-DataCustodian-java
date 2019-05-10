/*
 *     Copyright (c) 2018 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package org.greenbuttonalliance.espi.datacustodian.web;

import org.greenbuttonalliance.espi.common.domain.RetailCustomer;
import org.greenbuttonalliance.espi.common.domain.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class DefaultController extends BaseController {

	@RequestMapping(value = Routes.DEFAULT, method = RequestMethod.GET)
	public String defaultAfterLogin(HttpServletRequest request,
			Principal principal) {
		if (request.isUserInRole(RetailCustomer.ROLE_CUSTODIAN)) {
			return "redirect:/custodian/home";
		} else if (request.isUserInRole(RetailCustomer.ROLE_USER)) {
			return "redirect:/RetailCustomer/"
					+ currentCustomer(principal).getId() + "/home";
		}
		return "redirect:/home";
	}
}