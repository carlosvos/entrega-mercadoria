# entrega-mercadoria
 Webservice para cadastro de malha logística e consulta do menor caminho de um ponto a outro utilizando uma malha cadastrada.
 
 <h1>Motivação</h1>
 <p>Fui motivado a desenvolver este projeto pelo desafio de encontrar uma solução para um problema onde eu estava livre para utilizar as ferramentas que julgasse necessário. A possíbilidade de explorar e testar novos frameworks e APIs fizeram com que eu me dedicasse e me esforçasse mais para alcançar o objetivo esperado. Pude conversar e trocar idéias com amigos e colegas a respeito deste desafio o que me motivou ainda mais para a conclusão deste projeto. Espero que este meu esforço e dedicação possam ajudar e motivar outras pessoas a superar seus limites e concluir seus desafios.</p>
 
 <h1>Especificações</h1>
 <p>Desenvolvi este projeto utilizando frameworks e APIs que já tinha alguma experiência de uso e alguns outros que tive a oportunidade de estuda-los e experimenta-los neste projeto, a citar:</p>
  - spring;
  - resteasy;
  - neo4j;

Para deploy da aplicação, utilizei o JBOSS em sua versão 4.3.GA, pois me senti mais confortável utilizando-o uma vez que é uma ferramenta de uso padrão em meu ambiente de trabalho.

Além das ferramentas já citadas, também utilizei o código de referência para execução dos cálculos matemáticos do site http://algs4.cs.princeton.edu/44sp/, mais precisamente o algoritmo de Dijkstra para o cálculo do menor caminho em um grafo de vertices conectados por arestas de difentes pesos. Optei pelo uso do código já escrito no site devido a complexidade e estudos existentes a cerca deste algoritmo. 

Esta aplicação expõe dois serviços. Um para cadastro de uma malha logística, juntamente com suas rotas, e um para consulta do menor caminho entre um ponto de origem e um ponto de destino.

As chamadas dos serviços devem ser feitas através do método POST e tem sempre os seguintes formatos:
<ul>
<li>http://{seu_dominio}/entrega-mercadoria/services/malhaLogistica/cadastraMalhaLogistica -- cadastro</li>
<li>http://{seu_dominio}/entrega-mercadoria/services/malhaLogistica/consultaMenorCaminho -- consulta</li>
</ul>
Todas as informações trocadas com o webservice podem ser no formato JSON ou XML e os objetos de entrada e saída dos mesmo são os que se seguem:

O objeto a ser fornecido ao serviço de cadastro tem o seguinte formato:

<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%">{
  <span style="color: #008000; font-weight: bold">&quot;nomMalhaLogistica&quot;</span>:<span style="color: #BA2121">&quot;aa...a&quot;</span>,
  <span style="color: #008000; font-weight: bold">&quot;rotas&quot;</span>:[
    {
      <span style="color: #008000; font-weight: bold">&quot;origem&quot;</span>:<span style="color: #BA2121">&quot;oo...o&quot;</span>,
      <span style="color: #008000; font-weight: bold">&quot;destino&quot;</span>:<span style="color: #BA2121">&quot;dd...d&quot;</span>,
      <span style="color: #008000; font-weight: bold">&quot;distancia&quot;</span>: <span style="border: 1px solid #FF0000">nn...n</span>
    }, <span style="border: 1px solid #FF0000">...</span>
  ]
}
</pre></div>


O objeto de saída para o cadastro será uma mensagem de sucesso ou erro referente a operação. No caso de erro, será descrito o motivo da operação ter falhado.

Para a consulta do menor caminho, o formato deve ser o seguinte:
<div style="background: #f8f8f8; overflow:auto;width:auto;border:solid gray;border-width:.1em .1em .1em .8em;padding:.2em .6em;"><pre style="margin: 0; line-height: 125%">{
  <span style="color: #008000; font-weight: bold">&quot;nomMalhaLogistica&quot;</span>:<span style="color: #BA2121">&quot;aa...a&quot;</span>,
  <span style="color: #008000; font-weight: bold">&quot;origem&quot;</span>: <span style="color: #BA2121">&quot;oo...o&quot;</span>,
  <span style="color: #008000; font-weight: bold">&quot;destino&quot;</span>: <span style="color: #BA2121">&quot;dd...d&quot;</span>,
  <span style="color: #008000; font-weight: bold">&quot;autonomia&quot;</span>: <span style="border: 1px solid #FF0000">xx...x</span>,
  <span style="color: #008000; font-weight: bold">&quot;vlrCombustivel&quot;</span>: <span style="border: 1px solid #FF0000">vv...v</span>
}
</pre></div>

O objeto de saída para a consulta será uma mensagem no seguinte formadto:
<br></br>
"A rota ??...? com custo de tt...t."
<br></br>
No caso de erro durante a execução da operação, será retornada uma mensagem descrevendo o motivo da falha.

Nas representações de objetos acima, os significados são:
<ul>
<li>aa...a - nome da malha logística onde as rotas serão salvas ou procuradas;</li>
<li>oo...o - nome do local de origem onde a rota se inicia ou a partir de onde será feita a busca do menor caminho;</li>
<li>dd...d - nome do local de destino onde a rota termina ou até onde será feita a busca do menor caminho;</li>
<li>xx...x - quantidade de quilômetros que o veículo percorre por litro de combustível;</li>
<li>vv...v - valor do litro do combustível;</li>
<li>??...? - sequência de nomes das localidades exibidas de acordo com as rotas onde foram registradas. Começando pelo nome da            origem e terminando no nome do destino, conforme fornecido na requisição;</li>
<li>tt...t - custo total para percorrer a rota do menor caminho. Este cálculo é feito com base na distância total do percurso do          menor caminho, o valor da autonomia do veículo e o preço do litro do combustível.</li>
</ul>
