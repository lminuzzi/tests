import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //System.out.println(GAP.solutionGAP(9));
//        System.out.println(BitConform.solution(1073741727, 1073741631, 1073741679));
//        System.out.println(LocalDate.of(2025, Month.MARCH, 1).plusMonths(1).minusDays(1));

        //Cálculo do n/DAC, onde DAC = número de dias do ano civil e N = numero de dias corridos do mês
        YearMonth yearMonth = YearMonth.of(2025, Month.MARCH);
        int dac = yearMonth.lengthOfYear();
        int numDiasMes = yearMonth.lengthOfMonth();
        BigDecimal nDac = BigDecimal.valueOf(numDiasMes).divide(BigDecimal.valueOf(dac), 20, RoundingMode.HALF_EVEN).setScale(20, RoundingMode.HALF_EVEN);

        // Calculo do fator custo de captação
        BigDecimal custoFonteRecursoAoAno = BigDecimal.valueOf(11.959059513);

        BigDecimal alfa = BigDecimal.ONE;
        BigDecimal divisor = BigDecimal.valueOf(100);
        // Custo Fonte Recurso no formato não percentual para o cálculo, truncado em 9 casas
        BigDecimal tms = custoFonteRecursoAoAno.divide(divisor, 10, RoundingMode.HALF_EVEN).setScale(9, RoundingMode.DOWN);
        BigDecimal cf = alfa.multiply(tms).setScale(20, RoundingMode.HALF_EVEN);
        // CAT no formato não percentual para o cálculo
        BigDecimal cat = BigDecimal.valueOf(2.7500).divide(divisor, 20, RoundingMode.HALF_EVEN).setScale(20, RoundingMode.HALF_EVEN);

        System.out.println("alfa = " + alfa);
        System.out.println("tms = " + tms);
        System.out.println("cf = " + cf);
        System.out.println("cat = " + cat);
//        alfa = 1
//        tms = 0.119590595
//        cf = 0.11959059500000000000
//        cat = 0.02750000000000000000
//        nDac:0.08493150684931506849

        BigDecimal custoCaptacao = BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(cf).add(cat).doubleValue(), nDac.doubleValue())).setScale(20, RoundingMode.HALF_EVEN);
        // Convertendo para percentual
        BigDecimal custoCaptacaoPerc = (custoCaptacao.subtract(BigDecimal.ONE)).multiply(divisor).setScale(20, RoundingMode.HALF_EVEN);
        // sem arredondamento. Math Round usa toEven
        BigDecimal fatorCustoCap = custoCaptacaoPerc.setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal fatorCustoCaptacaoNaoPerc = custoCaptacao.setScale(14, RoundingMode.HALF_EVEN);

        // Calculo do fator receita de aplicação
        // Taxa no formato não percentual para o cálculo
        BigDecimal tx = BigDecimal.valueOf(12.0000).divide(divisor).setScale(20, RoundingMode.HALF_EVEN);
        System.out.println("nDac:" + nDac);
        System.out.println("nDac2:" + BigDecimal.ONE.add(tx).doubleValue());
        BigDecimal receitaAplicacao = BigDecimal.valueOf(Math.pow(BigDecimal.ONE.add(tx).doubleValue(), nDac.doubleValue())).setScale(20, RoundingMode.HALF_EVEN);
        // Convertendo para percentual
        BigDecimal receitaAplicacaoPerc = (receitaAplicacao.subtract(BigDecimal.ONE)).multiply(divisor).setScale(20, RoundingMode.HALF_EVEN);
        BigDecimal fatorReceitaApli = receitaAplicacaoPerc.setScale(10, RoundingMode.HALF_EVEN);
        BigDecimal fatorReceitaAplicacaoNaoPerc = receitaAplicacao;

        // Cálculo do montante a equalizar
        BigDecimal montanteEqualiz = BigDecimal.valueOf(49122153.28)
                .multiply(custoCaptacao.subtract(receitaAplicacao)).setScale(20, RoundingMode.HALF_EVEN);
        System.out.println("sub: " + custoCaptacao.subtract(receitaAplicacao));
        System.out.println("fatorCustoCap = " + fatorCustoCap);
        System.out.println("fatorCustoCaptacaoNaoPerc = " + fatorCustoCaptacaoNaoPerc);
        System.out.println("custoCaptacao = " + custoCaptacao);
        System.out.println("receitaAplicacao = " + receitaAplicacao);
        System.out.println("receitaAplicacaoPerc = " + receitaAplicacaoPerc);
        System.out.println("fatorReceitaApli = " + fatorReceitaApli);
        System.out.println("fatorReceitaAplicacaoNaoPerc = " + fatorReceitaAplicacaoNaoPerc);
        System.out.println(montanteEqualiz);

//        sub: 0.00205158815420290000
//        fatorCustoCap = 1.1723235151
//        fatorCustoCaptacaoNaoPerc = 1.01172323515068
//        custoCaptacao = 1.01172323515068
//        receitaAplicacao = 1.00967164699647680000
//        receitaAplicacaoPerc = 0.96716469964768000000
//        fatorReceitaApli = 0.9671646996
//        fatorReceitaAplicacaoNaoPerc = 1.00967164699647680000

        // SE A LINHA DE FINANCIAMENTO TEM O SEGUNDO SEQ STN, FAZ O CALCULO PARA CRIAR UM CALCULO DE EQUALIZAÇÃO COM ESSE SEGUNDO SEQ STN
        List<Integer> listaSegundoSeqStn = new ArrayList<>();
        listaSegundoSeqStn.add(1111);
        listaSegundoSeqStn.add(2222);
//        for(Integer operacaoEq : listaDeOperacoes) {
//            if (this.contarPorDataContratacao(operacaoEq.getNumCedulaCreRur(), datasNovoSeqStn) > 0) {
//                listaSegundoSeqStn.add(operacaoEq);
//            }
//        }

        BigDecimal msd = BigDecimal.valueOf(49122153.28);
        BigDecimal montante = montanteEqualiz;
        BigDecimal totalOperacoes = BigDecimal.valueOf(39);
        BigDecimal tamanhoLista = BigDecimal.valueOf(37);
        BigDecimal tamanhoListaSec = BigDecimal.valueOf(listaSegundoSeqStn.size());

        System.out.println("totalOperacoes: " + totalOperacoes);
        //Nesse momento há duas listas, as operações dentro do seqStn original e a lista do segundo seqStn
        //Nesse ponto divide os valores e cria mais um objeto TOOpeCalcEqualDetalhe

        System.out.println("msd: " + msd);
        msd = msd.divide(totalOperacoes, 20, RoundingMode.HALF_EVEN)
                .multiply(tamanhoLista)
                .setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("montanteEqualiz antes: " + montanteEqualiz);
        System.out.println("montanteEqualiz antes divisao normal: " + montante.divide(totalOperacoes, 20, RoundingMode.HALF_EVEN));
        System.out.println("montanteEqualiz antes divisao 2 casas: " + montante.divide(totalOperacoes, 2, RoundingMode.HALF_EVEN));
        System.out.println("montanteEqualiz antes 2 casas: " + montante.divide(totalOperacoes, 2, RoundingMode.HALF_EVEN)
                .multiply(tamanhoLista)
                .setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("resultado 1: " + BigDecimal.valueOf(2584.06225072274692360287).multiply(BigDecimal.valueOf(100778.42777818713002051200)));
        System.out.println("resultado 2: " + BigDecimal.valueOf(2584.06).multiply(BigDecimal.valueOf(100778.42777818713002051200)));
        System.out.println("resultado 1: " + BigDecimal.valueOf(2584.06225072274692360287).multiply(BigDecimal.valueOf(37)));
        System.out.println("resultado 2: " + BigDecimal.valueOf(2584.06).multiply(BigDecimal.valueOf(37)));
        //100778,42777818713002051200
        montanteEqualiz = montante.divide(totalOperacoes, 20, RoundingMode.HALF_EVEN)
                .multiply(tamanhoLista)
                .setScale(2, RoundingMode.HALF_EVEN);
        int qtdOperacoes = tamanhoLista.intValue();

        System.out.println("montanteEqualiz: " + montanteEqualiz);

        BigDecimal fatorReceitaApli2 = fatorReceitaApli;
        BigDecimal fatorReceitaAplicacaoNaoPerc2 = fatorReceitaAplicacaoNaoPerc;
        BigDecimal fatorCustoCap2 = fatorCustoCap;
        BigDecimal fatorCustoCaptacaoNaoPerc2 = fatorCustoCaptacaoNaoPerc;
        int qtdOperacoes2 = tamanhoListaSec.intValue();
        BigDecimal msd2 = msd.divide(totalOperacoes, 20, RoundingMode.HALF_EVEN)
                .multiply(tamanhoListaSec)
                .setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("montante2 2 casas:" + montante.divide(totalOperacoes, 2, RoundingMode.HALF_EVEN)
                .multiply(tamanhoListaSec)
                .setScale(2, RoundingMode.HALF_EVEN));
        BigDecimal montanteEqualiz2 = montante.divide(totalOperacoes, 20, RoundingMode.HALF_EVEN)
                .multiply(tamanhoListaSec)
                .setScale(2, RoundingMode.HALF_EVEN);

        System.out.println("montanteEqualiz2: " + montanteEqualiz2);

        BigDecimal montanteDop = BigDecimal.valueOf(4675007.28).multiply(
                BigDecimal.valueOf(1.01172323515068).subtract(BigDecimal.valueOf(1.00967164699647680000))
        );
        System.out.println(montanteDop);

        BigDecimal montanteDop2 = BigDecimal.valueOf(710780.36).multiply(
                BigDecimal.valueOf(1.01172323515068).subtract(BigDecimal.valueOf(1.00967164699647680000))
        );
        System.out.println(montanteDop2);

    }
}
