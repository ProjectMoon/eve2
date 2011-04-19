package eve.hooks;

import eve.core.EveObject;

/**
 * Register a hook to do arbitrary things to EveObjects at arbitrary times.
 * @author jeff
 *
 */
public interface EveHook {
	public void instrument(EveObject eo);
}
