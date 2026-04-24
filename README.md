# Retroalimentación

## A00405150

1. Excelente
2. La respuesta es buena, aunque JSESSIONID no es un token de autenticación. Es una cookie que almacena el identificador de la sesión de modo que el server puede recuperar la sesión.
3. Está bien aunque pudo tener mayor detalle. Por ejemplo que voy a la DB, produzco un UserDetails para que este sea almacenado en la sesión.
4. La solución presentada no corrige el error. Para corregirlo necesitabas usar @PathVariable para soportar el patrón de URL.
5. Excelente
6. Cuando usas hasRole o hasAnyRole, no debes usar el prefix ROLE_
7. El form está excelente, pero el server no. En el server creas el objeto vacío y bien, pero no lo ponen en la plantilla con model.addAttribute. Y en la recepción se usa @ModelAttribute para recibir el objeto llenado por el usuario
8. Al @PreAuthorize le hace falta por dentro los método hasRole y hasAuthority. Obviando esto, el primer método bien, el segundo bien, el tercero bien pero también puedes usar un @PreAuthorize("hasAuthority('DELETE_COURSE')"), el cuarto bien
9. Para poner varios roles separados por coma, necesitas el hasAnyRole, aquí también usar el prefijo ROLE_, pero funcionan las reglas bien
10. Excelente

---

## A00403480

1. Excelente
2. Buena respuesta
3. Aunque la respuesta en términos generales es excelente, se pudo especificar que el resultado de ir a la base de datos es la de almacenar un Principal en la sesión del usuario a través de un objeto de UserDetails que se produce.
4. Excelente
5. Excelente
6. Excelente
7. El GET lo tienes con un user de entrada sin tipo de variable. La forma correcta es usar una nueva instancia de la entidad e inyectarla tal como lo hiciste
8. Primera regla bien, en la segunda regla usar role para definirla y usar el rol de teacher, pero también se puede editar por parte del admin. Tercera regla bien, y en la lista de cursos, solo lo puede hacer el estudiante, pero acorde a las reglas todos los roles lo pueden hacer
9. Si quieres poner varios roles, usar hasAnyRole
10. Excelente

---

## A00405613

1. Excelente
2. Excelente, pero cuidado que pintaste las sesiones por fuera del ServletContainer (Catalina), pero realmente es una gran descripción.
3. Excelente
4. Excelente
5. Esta bien que saques las etiquetas th en el HTML que recibe el server, pero `<li text="Alfa"></li>` es un error, realmente queda como `<li>alfa</li>`.
6. Excelente
7. Excelente
8. Excelente
9. Excelente
10. Excelente

---

## A00402149

1. Valgo la mitad del punto porque mencionas hashing, pero encriptar la contraseña es inseguro. La técnica adecuada es hashing
2. Mencionas que quien decide si se puede usar el recurso o no es el controller, pero esto no es así. Es el SecurityFilterChain. Valgo puntos porque en términos generales, las salidas de los flujos son correctas
3. El flujo descrito no tiene los bloques ordenados correctamente y no se explica el papel de UserDetails ni como pasa ser un registro en base de datos a un usuario de la aplicación de springboot
4. La respuesta es equivocada, el error se soluciona con @PathVariable
5. La respuesta es equivocada. Lo que llega a la plantilla es la información ya procesada
6. Hay una regla bien, pero no debes usar el prefijo ROLE_ cuando usar hasRole. La segunda regla le hace falta el otro rol.
7. No se genera el mecanismo de form
8. Bien, fallos de sintaxis, pero se evidencia el conocimiento
9. Bien, pero en la regla final hay un error. Usas hasRole, pero realmente había que usar hasAuthority
10. Excelente

---

## A00405124

1. La respuesta es cierta, pero insuficiente
2. El orden de los elementos no es el correcto
3. El orden de los elementos expuesto no es correcto
4. La corrección no es correcta, un requestparam no tiene ese patrón de URL
5. Bien
6. No es correcto el método que tienes, pero valgo que sí se aplican las reglas
7. Nada
8. Nada
9. El nombre de los métodos no es el correcto
10. Bien

---

## A00405037

1. La respuesta es equivocada, además BCrypt es un algoritmo de hashing y no de encriptación.
2. Hay un error conceptual y es que Catalina es un Contenedor de Servlets, por lo tanto tiene dentro el servlet dispatcher del que hablas y ubicas el security Filter Chain antes después del servlet, pero no es así.
3. Correcto, difícil entender basado en la redacción, pero el flujo general es adecuado. No se muestran los 2 flujos: el cliente pide inicialmente el formulario de login y luego cuando el usuario usa el formulario.
4. La corrección es incorrecta
5. El HTML no es correcto, la etiqueta each es de thymeleaf, por lo cual no llega al cliente. Y `<p>` no tiene un atributo text.
6. Las reglas son correctas, pero no deben finalizar en permitAll(). Si quieres permitir varios roles debes usar hasAnyRole.
7. La solución presentada es incorrecta
8. Nada
9. Las reglas son correctas, pero no se usa bien hasAuthority y hasRole. Si usas hasAuthority debes poner ROLE_ como prefijo dado que en la tabla suministrada aparece así.
10. Está casi correcto. Tu filter chain permite acceso a todo sin excepción, es más adecuado usar la regla solo para liberar /landing y el resto (anyRequest) dejarlo como authenticated.

---

## A00402515

1. No es la respuesta correcta, debe estar hasheada.
2. En la respuesta no incluyes los elementos requeridos que debías mencionar
3. La respuesta no incluye los elementos requeridos, no se describe el flujo del request en el componente.
4. La corrección está mal
5. Nada
6. Lo que pusiste está bien
7. Nada
8. Nada
9. Nada
10. Nada

---

## A00405059

1. No es correcto
2. No se menciona el papel del FilterChain, Coyote, Catalina y otros componentes requeridos en la respuesta
3. El orden presentado no es correcto ni se mencionan los elementos requeridos en la respuesta
4. La corrección es incorrecta
5. El HTML recibido que pones es incorrecto. h1 no tiene el atributo text y el h3 con el arreglo no muestra bien la respuesta. Valgo que quitaste las etiquetas th
6. Las reglas establecidas son incorrectas, se puede hacer con hasRole o hasAnyRole
7. Nada
8. Hay algo de idea, pero no usas el hasRole o hasAnyRole, las reglas pueden estar mejor si usas hasAuthority y pones el permiso correspondiente
9. Nuevamente no usas hasRole o hasAuthority para definir bien las reglas.
10. La corrección y argumentos presentados es incorrecta.

---

## A00401525

1. La respuesta es correcta, pero el argumento no es claro. La razón es la irreversibilidad del hashing.
2. En el primer flujo, el orden es correcto, en el segundo flujo no. La razón es que el SecurityFilterChain va antes del servlet, pero es buena la respuesta
3. Falta la descripción paso a paso, algunos de los bloques realmente no son pasos del flujo general como el UserDetails, que es un productor del UserDetailService.
4. Es incorrecto
5. Es incorrecto, el cliente ya recibe la información procesada por Thymeleaf, entonces no debería llegar ninguna etiqueta th.
6. En la segunda regla falta el admin, que también tiene acceso a /user/**
7. Es incorrecto
8. Nada
9. Faltan reglas: /course/edit puede entrar admin o teacher y solo se da acceso a teacher
10. Bien

---

## A00403846

1. La respuesta es incorrecta, hablas como si encriptar y hashear fuera lo mismo
2. Bien
3. El orden de los elementos está mal, primero pasa por SecurityFilterChain antes del ApplicationContext. Tampoco hay descripción
4. Bien, pero la URL pudo haber tenido un número para el ejemplo
5. Bien
6. Bien, el método preciso para poner varios roles es hasAnyRole
7. Nada
8. Nada
9. Nada
10. Nada

---

## A00373171

1. Aunque es correcto, no hay una afirmación de que el hasheo es mejor, el argumento de que es mejor computacionalmente no lo hace mejor alternativa. La razón es que la operación es irreversible
2. Bien
3. Bien
4. Bien
5. Bien
6. Bien
7. Bien
8. Bien
9. Bien
10. Bien

---

## A00370236

1. Buena respuesta, aunque tener en cuenta que los filtros de seguridad y la forma de procesar la contraseña son diferentes
2. Es correcta la respuesta, aunque no se mencionan los componentes que participan en el flujo en el escenario no autenticado. En el escenario autenticado es correcto, pero el nivel de detalle no es el esperado. Pareciera como si la base de datos fuera quien validara si el intento de login es válido.
3. Hay bajo nivel de detalle, usar un bloque GET como si fuera un componente que procesa la solicitud, pero no es así. El siguiente flujo es correcto, pero no veo una descripción completa.
4. Bien
5. Nada
6. La regla de admin bien, la regla de /user/** es incorrecta porque el admin no puede ingresar. La regla del resto autenticado aunque el método es incorrecto, se entiende la intención
7. Nada
8. Si, era con @PreAuthorize
9. Casi correcto. Puedes usar hasAuthority ya sea Role o Permission. En este caso si usar hasRole debes omitir el ROLE_ y si usas hasAuthority debes poner el permiso completo.
10. El primer statement de la respuesta es correcto, pero el 403 NO ocurre porque espere un JSessionID, no pusiste la corrección

---

## A00403752

2, 3. En el punto 2 y 3 mezclas un poco los flujos de ir por un recurso y el flujo de login. Lo que está escrito está bien, pero no en el orden adecuado. Si voy por un recurso realmente nunca voy al UserDetailService, cuando voy por un recurso simplemente miro mi sesión y verifico que tenga un UserDetail. Si no lo tengo, no accedo, si lo tengo accedo al AppContext donde están los recursos. Cuando me logueo sí voy a UserDetailService porque necesito poner el UserDetails y anexarlo a la sesión.

1. Bien
4. Es incorrecto, el @RequestParam es incompatible con el patrón "/user/{id}".
5. Bien
6. El método es hasAuthority o hasAnyAuthority, pero se ve la intención
7. En esta parte se debe cargar al model un usuario vacío, para insertarlo en el form
8. Nada
9. Bien
10. Bien

---

## A00381746

1. Bien
2. El coyote hace parte de tomcat, en la respuesta se afirma lo contrario. El nivel de detalle de la respuesta no es el esperado dado que no mencionas elementos clave como la sesión o el userdetail, pero sí haces mención al AuthenticationManager. En la respuesta mezclas el flujo de login como si hiciera parte de ir a pedir un recurso, pero no es así
3. Bien. De hecho tienes elementos que me parecen mejor respuesta que el punto 2
4. Bien
5. Se esperaba el HTML acorde a la pregunta. Me muestras lo que ve el navegador, no lo que recibe el navegador
6. Cuando usas hasRole debe omitir el ROLE_. Si usas hasAuthority no lo debes omitir
7. Bien
8. No está del todo bien. En la capa de service puedes usar los permisos y prescindir de los roles. Las reglas están bien aplicadas. Usas hasAnyRole para indicar que cualquier rol lo puede acceder, pero es importante que anotes exactamente el conjunto de usuarios que lo pueden hacer. No hacerlo, significa una brecha de seguridad.
9. Bien
10. Bien

---

## A00404150

1. Bien
2. Bien, solo tener en cuenta que dentro de Catalina también van los securityFilterChain.
3. Bien
4. El patrón de URL "/user/{id}" no es compatible con @RequestParam.
5. Bien
6. Bien
7. Bien la parte de controller, no completaste el form
8. Bien
9. Bien

---

## A00405090

1. Bien
2. El orden de los bloques no es correcto, hay imprecisiones como que no se establece si viaja o no el JSessionID a partir de la request, o que el filterchain es posterior al ServletDispatcher
3. La respuesta no es precisa. No se establece el flujo desde el GET. Las afirmaciones son en su mayoría correctas, pero no se mencionan los bloques requeridos en la pregunta.
4. El patrón "/user/{id}" no es compatible con @RequestParam. Sin embargo la URL suministrada está bien
5. Bien
6. Las reglas están bien excepto para la URL /user/** porque el admin no puede ingresar, pero sí debería.
7. El form está bien, pero repites el field username para los dos input, en el GET no pasas el objeto vacío que acorde a tu solución debería llamarse user y en el post no está el ModelAttribute
8. Hay 3 reglas bien y 1 incompleta.
9. Bien
10. Bien, pero hizo falta en la solución poner el authenticated() al final. El error no es que tuviera ese Authenticated, es que estaba trocado con la regla de permitAll de abajo.

---

## A00402639

1. Bien
2. Nada
3. El orden no es el correcto, la respuesta es un poco ambivalente. Se mencionan elementos que son ciertos, pero con imprecisiones moderadas.
4. La corrección es equivocada
5. Bien, problemas con HTML, pero al final bien
6. Los métodos usados no son los específicos. Debería ser hasAuthority o hasAnyAuthority.
7. Nada
8. Nada
9. Hay imprecisiones, si usas hasAuthority debes mencionar el permiso el rol como ROLE_ADMIN y no como ADMIN. Si usas el hasRole debes usar ADMIN sin el prefijo ROLE_
10. Bien

---

## A00399865

1. La respuesta es equivocada, la contraseña se debe almacenar por medio de hashing
2. El orden de los elementos mencionados es equivocado, aunque es correcto que devuelve 403 en caso de que no suceda.
3. La respuesta está mal, el orden mencionado de los elementos es incorrecto. Además que se menciona que enviar username y password hacia el servidor luego de llenado el form corresponde a una petición GET. La mención de UsernamePasswordAuthenticationFilter tampoco es aceptada
4. No se corrige el endpoint, por lo tanto la URL escrita no funciona
5. Al servidor no llegan las etiquetas th dado que esto fue procesado en el servidor
6. Las reglas no cumplen con lo establecido. Valgo 0,2 puntos de 4 por la regla del anyRequest().
7. No se presenta nada
8. Las etiquetas usadas no son las correctas
9. Las reglas usadas no son correctas, corresponde a métodos no válidos
10. El argumento dice que es una contradicción, pero realmente el error está en el orden de las líneas. La corrección está mal

---

## A00406386

1. La respuesta es equivocada, debería usarse hash
2. Nada
3. El orden de mención del flujo es incorrecto. Hay conocimiento en la respuesta, pero le hace falta precisión
4. El patrón de url /user/{id} es incompatible con @RequestParam. Ese era el error
5. Bien
6. El método que usas (permitAll) es un método que no recibe los Strings que estás poniendo, pero entiendo la intención
7. Nada
8. Es más adecuado manejar los permisos en la capa de servicio, en el último método no especificas que todos los usuarios son los que pueden entrar. No puedes simplemente poner "@Security", sino que debes mencionar quién puede entrar y quién no.
9. Nuevamente el método usado debería ser hasRole o hasAuthority
10. Bien

---

## A00404253

1. Bien
2. Bien
3. Bien, aunque el foco pudo ser describir mejor el proceso del UserDetailService.
4. Casi todo bien, excepto porque el patrón /user/{id} no es compatible con RequestParam, sino con PathVariable
5. Bien
6. Bien
7. Bien
8. Bien, aunque la forma más simple y elegante es usar únicamente hasAnyAuthority en cada método y listar los permisos del método
9. Bien
10. Bien

---

## A00407975

1. Bien
2. Bien, aunque esperaba la mención del UserDetails o al menos de la HTTP Session para establecer cómo es que el servidor conoce al usuario que envía la solicitud.
3. Bien la pregunta, me hizo falta un poco describir el esquema, pero es correcto. No se especifica la aparición del userDetails, ni como este queda relacionado con una sesión HTTP almacenada por el server
4. La corrección no es válida
5. Bien
6. El uso del método no es el correcto, pero las reglas están bien aplicadas
7. La respuesta es incorrecta
8. Entiendo la intención, pero la forma en la que expresas la solución no es adecuada. Primero, usas los roles y no los permisos que es lo más adecuado y luego porque no usas la etiqueta @PreAuthorize.
9. Faltó una regla y nuevamente no usas hasRole o hasAuthority
10. Bien

---

## A00403696

1. Bien
2. Bien, aunque el orden es incorrecto. DispatcherServlet se hace después de que se comprueban las reglas en los SecurityFilterChain.
3. El securityfilterchain lo pones dentro de AppContext lo cual es incorrecto. El esquema presentado es el correcto y la conexión con UserDetailService está bien
4. Nada
5. Es correcto, pero hay errores HTML que deberían estar. Por ejemplo usar la etiqueta `<p>` pero la etiqueta que se multiplica es la `<li>`
6. Las reglas no son correctas. Valgo 2 de 4 puntos ya que tienes el authenticated, pero el "permitAdmin()" es incorrecto.
7. Nada
8. Nada
9. Las reglas presentadas son incorrectas
10. Es correcto, pero no presentas la corrección

---

## A00400293

1. Bien
2. Algunos statements son correctos, pero el orden que presentas no es adecuado. La respuesta indica que el servlet es quien activa el securityFilterChain, pero no es cierto. La petición pasa primero por los filterchain y si pasa, va hacia el dispatcher servlet
3. La secuencia presentada es correcta, pero falta mayor descripción. Valgo puntos porque efectivamente muestras que se hace el UserDetails a partir de los datos almacenados en DB.
4. Bien
5. Bien
6. Bien
7. Nada
8. Nada
9. Nada
10. Nada

---

## A00170732

1. Bien
2. Bien, solo quitaría el paso de Catalina procesa el contenedor de Servlets, no va el paso realmente. Falta algo de precisión, pero es muy buena la respuesta
3. Super
4. Bien
5. No procesas bien la lista
6. Las reglas no están del todo bien. Según tus reglas el admin no puede ingresar a /user/** lo cual es incorrecto
7. Nada
8. Ibas bien, ¿qué pasó?
9. Bien
10. Nada

---

## A00407362

1. Bien
2. Bien, aunque realmente el SecurityFilterChain va antes que el ServletDispatcher. No hay mayor detalle en la descripción de cómo es que el server sabe cómo está autenticado o no. Faltó descripción en el mecanismo: JSessionID, Http Session y Principal/UserDetails.
3. Bien, aunque nuevamente la descripción no es tan detallada
4. No hay corrección del endpoint. La URL está mal porque "/user/{id}" no es compatible con @RequestParam.
5. Pedía el HTML que recibe el navegador. No cómo lo renderiza el navegador
6. Las reglas están casi bien, pero por ejemplo /user/** solo puede acceder el USER, pero un ADMIN no puede acceder acorde a lo que has escrito
7. Bien
8. Nada
9. Tienes bien 3 reglas de las 4 requeridas.
10. Bien

---

## A00405157

1. Bien
2. Bien
3. Bien, aunque en el flujo no queda claro que son dos Request/Response.
4. Está casi bien, excepto que de extra está {id}. Tu endpoint funciona sin ese patrón en la URL, el cual es solo compatible con @PathVariable
5. Bien
6. Bien
7. Instancias de forma directa UserService, es un error. No inyectas el user vacío en el form. La plantilla está bien. El post está mal porque recibe el UserService de entrada, lo cual es un error.
8. Está bien, aunque es preferible usar los permissions.
9. Bien
10. Bien

---

## A00407054

1. Los algoritmos que mencionas son de hashing y no de encriptación. La respuesta no es correcta.
2. El orden de los elementos es incorrecto. Valgo la mitad del punto porque expones el punto donde se toma la decisión de dar o no el recurso
3. En general es correcto, pero el orden nuevamente es incorrecto. El nivel de detalle por ejemplo de cómo o donde se almacena el UserDetails no está.
4. El patrón de URL /user/{id} es incompatible con @RequestParam, por lo tanto es incorrecto.
5. Incorrecto
6. Bien
7. Incorrecto
8. Debías usar @PreAuthorize y además seleccionar si usas hasRole o hasAuthority
9. Bien
10. Bien

---

## A00407538

1. Bien
2. Lo que pusiste en general es correcto, pero en tu flujo dices que primero va al controller antes que al SecurityFilterChain lo cual es incorrecto. Además no mencionas todos los bloques requeridos en la respuesta.
3. Correcto, pero con imprecisiones. Por ejemplo UserDetails no implementa una interfaz UserDetailsService. Son dos elementos diferentes aunque se ve que tienes claro el mecanismo
4. Incorrecto, el patrón /user/{id} no es compatible con @RequestParam.
5. Incorrecto. Valgo que has sacado las etiquetas th, pero no muestras que los `<li>` se multiplican sino que los separas con comas en una propiedad llamada text que no existe para `<li>`. La forma correcta era `<li>Ana</li><li>Luis</li><li>Carlos</li>` para esa sección. `<p>` tampoco tiene una propiedad text
6. Las reglas tienen un defecto y es que /user/** solo puede acceder User, pero no Admin.
7. Nada
8. Nada
9. Bien
10. Casi, pero en la regla de anyRequest tienes un permitAll, lo cual es incorrecto

---

## A00403633

1. Bien
2. Hay una precisión y es que los servlets no tienen los SecurityFilterChain, de hecho es un paso anterior. El nivel de detalle con el que describes el proceso de SecurityContext y demás es muy bueno. En el caso sin autenticación el gráfico sí revela el orden correcto, pero en la descripción cuando sí está autenticado hay una imprecisión en la respuesta.
3. La respuesta es correcta
4. La URL casi bien, te sobra el =
5. Bien
6. Bien. Falla sintácticamente, pero no conceptualmente
7. No llenas el form. En el GET no lo haces bien porque ahí debías inyectar el nuevo usuario vacío para delegar al usuario a llenarlo por medio del formulario. En el POST no queda claro cómo o de donde sale la instancia de User. Asumo que lo pones de entrada al método.
8. Está bien y sacas el punto completo. Pero te excedes con las reglas. Solo eran necesarias la de Permission por medio de hasAuthority o hasAnyAuthority.
9. Bien conceptualmente, no sintácticamente
10. Bien
