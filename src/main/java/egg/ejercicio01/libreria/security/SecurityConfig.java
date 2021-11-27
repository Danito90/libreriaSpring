package egg.ejercicio01.libreria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import egg.ejercicio01.libreria.servicios.UsuarioServicio;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired //
    private BCryptPasswordEncoder bcrypt;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        return bcryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioServicio)
                .passwordEncoder(bcrypt);

    }
    // @Autowired
    // public void configurerGlobal(AuthenticationManagerBuilder auth) throws Exception { // autenticacion
    //     auth
    //         .userDetailsService(usuarioServicio)
    //         .passwordEncoder(passwordEncoder());
    // }// dice que servicio es que vamos a utilizar y setea un encriptador de
    //  // contraseña

     @Override
     protected void configure(HttpSecurity http) throws Exception{ //autorizacion
         http.headers().frameOptions().sameOrigin().and() 
            .authorizeRequests()     //
                .antMatchers("/css/*","/js/*","/img/*").permitAll()  // estos recursos son accesibles por todos si necesidad de estar logeados
                .antMatchers("/libro/**","/autor/**","/editorial/**","/prestamo/**","/","/foto/**").hasAnyRole("ADMIN","USUARIO") // bloqueando para solo usuarios registrados
                //debo agregar el path "/" para q cuand oinicio sin registrarme me lleve al login
                .antMatchers("/usuario/**").hasRole("ADMIN") // solo permitido para administrador
            .and().formLogin()
                .loginPage("/login")  // Url donde se encuentra el formulario login
                .loginProcessingUrl("/logincheck") // es la url que se usa en el formulario login para hacer el login
                .usernameParameter("username") // nombre con que viaja parametro de usuario
                .passwordParameter("password") // nombre con que viaja parametro de contraseña
                .defaultSuccessUrl("/") // Url donde se redirige despues de loguearse
                .permitAll()
            .and().logout()
                .logoutUrl("/logout") // Url para la salida de loguearse
                .logoutSuccessUrl("/login") // url donde se redirige despues de salirse
                .permitAll();
            
     }

     //asi digo cuales estan restringidos en ves de ser accesibles
    //  http.authorizeRequests()
    //  .antMatchers("/editar/**", "/agregar/**", "/eliminar")
    //  .hasRole("ADMIN") //estos son los unicos roles que pueden acceder a estos recursos
    //  .antMatchers("/") // al path rais puede acceder cualquiera
    //  .hasAnyRole("USER", "ADMIN")
}
