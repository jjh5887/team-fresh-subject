package com.example.teamfreshsubject.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.teamfreshsubject.account.Account;
import com.example.teamfreshsubject.account.AccountService;
import com.example.teamfreshsubject.compensation.CompensationService;
import com.example.teamfreshsubject.compensation.request.CompensationRequest;
import com.example.teamfreshsubject.penalty.PenaltyService;
import com.example.teamfreshsubject.penalty.request.PenaltyRequest;
import com.example.teamfreshsubject.penalty.response.PenaltyResponse;
import com.example.teamfreshsubject.voc.PartyCode;
import com.example.teamfreshsubject.voc.VocService;
import com.example.teamfreshsubject.voc.request.VocRequest;
import com.example.teamfreshsubject.voc.response.VocCreateResponse;

@Configuration
public class AppConfig {

	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {

			public final String[] driverNames = {
				"김가가", "김나나", "김다다", "김라라", "김마마", "김바바", "김사사", "김아아", "김자자"
			};
			@Autowired
			AccountService accountService;
			@Autowired
			VocService vocService;
			@Autowired
			CompensationService compensationService;
			@Autowired
			PenaltyService penaltyService;

			@Override
			public void run(ApplicationArguments args) throws Exception {
				List<Account> drivers = new ArrayList<>();
				for (int i = 0; i < driverNames.length; i++) {
					drivers.add(accountService.save(makeDriver(driverNames[i], i)));
				}
				List<Account> managers = new ArrayList<>();
				for (int i = 0; i < driverNames.length; i++) {
					String name = "정" + driverNames[i].substring(1);
					managers.add(accountService.save(makeManger(name, i)));
				}

				List<VocCreateResponse> vocResponses = new ArrayList<>();
				for (int i = 0; i < driverNames.length; i++) {
					vocResponses.add(
						vocService.create(VocRequest.builder()
							.party(PartyCode.values()[1])
							.description("테스트 배상 " + i)
							.driverId(drivers.get(i).getId())
							.managerId(managers.get(i).getId())
							.build()));
				}
				for (int i = 0; i < driverNames.length; i++) {
					vocService.create(VocRequest.builder()
						.party(PartyCode.values()[i % 2])
						.description("다른 테스트 배상 " + i)
						.driverId(drivers.get(i).getId())
						.managerId(managers.get(i).getId())
						.build());
				}

				for (int i = 0; i < vocResponses.size(); i++) {
					VocCreateResponse vocCreateResponse = vocResponses.get(i);
					if (vocCreateResponse.getParty() == PartyCode.CARRIER) {
						compensationService.create(CompensationRequest.builder()
							.amount(10000L * i)
							.vocId(vocCreateResponse.getId())
							.build());
						if (i % 4 == 0)
							continue;
						PenaltyResponse penaltyResponse = penaltyService.create(PenaltyRequest.builder()
							.description("페널티 액은" + 10000L * i + "원 입니다.")
							.vocId(vocCreateResponse.getId())
							.build());
						boolean objection = false;
						if (i % 3 == 0) {
							objection = true;
						}
						penaltyService.check(penaltyResponse.getId(), objection);
					}
				}
			}
		};
	}

	private Account makeDriver(String name, int idx) {
		return Account.builder()
			.partyCode(PartyCode.CARRIER)
			.company("우체국")
			.name(name)
			.contactNum("010-0000-000" + idx)
			.build();
	}

	private Account makeManger(String name, int idx) {
		return Account.builder()
			.partyCode(PartyCode.CLIENT)
			.company("K-애플")
			.name(name)
			.contactNum("010-1111-000" + idx)
			.build();
	}
}
