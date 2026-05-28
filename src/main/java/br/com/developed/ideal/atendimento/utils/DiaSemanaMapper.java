package br.com.developed.ideal.atendimento.utils;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DiaSemanaMapper {
	
	public static Integer getPeriodoAtual() {
		
		OffsetDateTime agora = OffsetDateTime.now(
				ZoneOffset
				.of("-03:00"));
		
		if (agora.getHour() >= 0 && agora.getHour() <= 1) {
			return 0;
		}
		
		if (agora.getHour() >= 2 && agora.getHour() <= 3) {
			return 2;
		}
		
		if (agora.getHour() >= 4 && agora.getHour() <= 5) {
			return 4;
		}
		
		if (agora.getHour() >= 6 && agora.getHour() <= 7) {
			return 6;
		}
		
		if (agora.getHour() >= 8 && agora.getHour() <= 9) {
			return 8;
		}
		
		if (agora.getHour() >= 10 && agora.getHour() <= 11) {
			return 10;
		}
		
		if (agora.getHour() >= 12 && agora.getHour() <= 13) {
			return 12;
		}
		
		if (agora.getHour() >= 14 && agora.getHour() <= 15) {
			return 14;
		}
		
		if (agora.getHour() >= 16 && agora.getHour() <= 17) {
			return 16;
		}
		
		if (agora.getHour() >= 18 && agora.getHour() <= 19) {
			return 18;
		}
		
		if (agora.getHour() >= 20 && agora.getHour() <= 21) {
			return 20;
		}
		
		return 22;
	}
	
	public static Integer getDiaSemana() {
		
		OffsetDateTime agora = OffsetDateTime.now(
				ZoneOffset
				.of("-03:00"));
		
		DayOfWeek diaSemana = agora.getDayOfWeek();
		
		switch (diaSemana) {
			case MONDAY: {
				return 0;
			}
			case TUESDAY: {
				return 1;
			}
			case WEDNESDAY: {
				return 2;
			}
			case THURSDAY: {
				return 3;
			}
			case FRIDAY: {
				return 4;
			}
			case SATURDAY: {
				return 5;
			}
			default: {
				return 6;
			}
		}
		
	}

}
