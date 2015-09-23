package com.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.model.Role;
import com.model.User;

public class ADAuthenticator {

    private String domain;
    private String ldapHost;
    private String searchBase;

    public ADAuthenticator() {
        this.domain = "HEMOMINAS";
        this.ldapHost = "ldap://10.14.124.11";
        this.searchBase = "dc=hemominas,dc=mg,dc=gov,dc=br";
    }

    public ADAuthenticator(String domain, String host, String dn) {
        this.domain = domain;
        this.ldapHost = host;
        this.searchBase = dn;
    }

    public User authenticate(String masp, String pass) {
    	User usuarioAd = new User();
    	
        String returnedAtts[] = {"sn", "givenName", "mail", "displayName"};
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + masp + "))";
        String displayName = "";
        
        //Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);

        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapHost);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, masp + "@" + domain);
        env.put(Context.SECURITY_CREDENTIALS, pass);

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            //Search objects in GC using filters
            NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);
            
            SearchResult searchResult = (SearchResult) answer.next();
            Attributes attributes = searchResult.getAttributes();
           
            Attribute attrCN = attributes.get("displayName");
            usuarioAd.setName((String) attrCN.get());
            
            attrCN = attributes.get("mail");
            usuarioAd.setEmail((String) attrCN.get());
            
            usuarioAd.setMasp(masp);
            
            usuarioAd.setRole(Role.ADMIN);
            
        } catch (NamingException ex) {
            ex.printStackTrace();
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return usuarioAd;
    }
}
