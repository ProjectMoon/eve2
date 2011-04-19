package eve.hooks;

import java.util.ArrayList;
import java.util.List;

import eve.core.EveObject;

public class HookManager {
	private static List<EveHook> cloneHooks = new ArrayList<EveHook>();
	
	/**
	 * Registers a hook to be called whenever an object or prototype is cloned.
	 * @param hook
	 */
	public static void registerCloneHook(EveHook hook) {
		cloneHooks.add(hook);
	}
	
	public static void callCloneHooks(EveObject eo) {
		for (EveHook hook : cloneHooks) {
			hook.instrument(eo);
		}
	}
}
