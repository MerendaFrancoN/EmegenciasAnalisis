Los directivos del hospital basados en el crecimiento demográfico de la región desean ampliar los servicios de la guardia del hospital.

Con este propósito, se ha tomado la dedición de realizar un estudio de simulación para evaluar el desempeño del servicio de guardia médica del hospital, ya que es un servicio crítico y ningún detalle puede ser dejado al azar.

Además de lo mencionado anteriormente, los directivos desean conocer:
    El tiempo medio de tránsito de los pacientes en el interior del hospital,
    El tiempo medio de espera en cola de cada tipo de atención que requieren los pacientes,
    El tiempo medio de ociosidad de cada especialista médico.

En base a la información con la se cuenta en la actualidad se pretende distribuir la
atención de la guardia médica, para la eso la cantidad de médicos que prestan servicio
será de la siguiente manera:
    Médicos Residentes: 2.
    Médicos Generalistas: 1.
    Médicos Especialistas: 2.

Los médicos residentes atenderán a los pacientes que se encuentran con una afección leve, los médicos generalistas atenderán a los pacientes con afección media y por último los médicos especialistas atenderán a los pacientes que se encuentran graves.

Cada paciente que llega a la guardia del hospital se dirigirá automáticamente al sector en donde será atendido de acuerdo a su cuadro clínico (leve, medio o grave) y allí será atendido por uno de los médicos o seleccionará la cola de espera más corta o bien la
primera si todas tienen la misma longitud, según su categoría.

Las tablas a continuación describen la distribución de probabilidades a la que responden los arribos, teniendo en cuenta que el estado del tránsito aéreo variará de acuerdo al horario, habiendo una mayor afluencia de vuelos en los periodos de 7 a 9 Hs. y de 20 a 22 Hs.

Tipo transito | Tpo. entre arribos (hh normal) | Prob | Tpo. entre arribos (hh pico) | Prob |
              |                20              | 0.3  |              10              | 0.5  |
    Leve      |                30              | 0.3  |              20              | 0.35 |
              |                40              | 0.3  |              30              | 0.15 |
---------------------------------------------------------------------------------------------
              |                60              | 0.25 |              40              | 0.35 |
    Medio     |                70              | 0.75 |              50              | 0.65 |
---------------------------------------------------------------------------------------------
              |                120             | 0.5  |              60              | 0.4  |
    Grave     |                180             | 0.5  |              90              | 0.6  |


La distribución de probabilidades del tiempo de atención de cada tipo de paciente es:

    Leve: Exponencial mu = 30
    Medio: Uniforme [10..20]
    Grave: Normal (120,30)

La cantidad de médicos por tipo de pacientes es el parámetro a ir ajustando para determinar la distribución óptima.

El periodo a simular consta de una semana, 168 horas.

Al principio de la simulación Ud. deberá generar tres arribos, uno por cada tipo de paciente y el evento de fin de simulación. Es importante destacar que cada evento de arribo de un determinado tipo generará un nuevo arribo del mismo tipo. Por ejemplo, un evento de arribo de paciente leve generará un nuevo arribo de paciente leve.