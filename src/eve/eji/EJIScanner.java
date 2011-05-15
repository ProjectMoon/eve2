package eve.eji;

import java.beans.IntrospectionException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import eve.core.EveObject;
import eve.core.builtins.EveGlobal;

@EJIType("test")
class Test {
	private String x;
	public Test(String x) {
		this.x = x;
	}
	
	public String getX() {
		return x;
	}
	
	public void setX(String x) {
		this.x = x;
	}
}

public class EJIScanner {
	private List<String> packages = new ArrayList<String>();
	
	public EJIScanner() {
		
	}
	
	public void addPackage(String packageName) {
		packages.add(packageName);
	}
	
	public void scan() {
		FilterBuilder fb = new FilterBuilder();
		Set<URL> pkgUrls = new HashSet<URL>();
		
		for (String pkg : packages) {
			fb.include(FilterBuilder.prefix(pkg));
			pkgUrls.addAll(ClasspathHelper.getUrlsForPackagePrefix(pkg));
		}
		
		ConfigurationBuilder cb =
			new ConfigurationBuilder()
			.filterInputsBy(fb)
			.setUrls(pkgUrls)
			.setScanners(new TypeAnnotationsScanner().filterResultsBy(fb));
		
		Reflections reflections = new Reflections(cb);
		
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(EJIType.class);
		try {
			createEJITypes(annotated);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createEJITypes(Set<Class<?>> types) throws InstantiationException, IllegalAccessException, IntrospectionException {
		for (Class<?> type : types) {
			EJIType typeInfo = type.getAnnotation(EJIType.class);
			EveObject eo = EJIHelper.createEJIConstructor(type);
			EveGlobal.addType(typeInfo.value(), eo);
		}
	}
}
