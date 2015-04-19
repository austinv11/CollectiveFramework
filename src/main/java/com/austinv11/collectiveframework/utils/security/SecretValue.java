package com.austinv11.collectiveframework.utils.security;

/**
 * This holds various info about an encryption/decryption.
 * Use the {@link Encryption} methods to instantiate and retrieve info from this object.
 * Note: This can be caller sensitive (if supported by the JDK)
 */
public final class SecretValue<V, K, M> {
	
	protected final V value;
	protected final K key;
	protected final M meta; //Any additional info
	protected final boolean isCallerSensitive;
	protected final String approvedCaller;
	
	protected SecretValue(V value, K key, M meta, boolean isCallerSensitive, String approvedCaller) {
		this.value = value;
		this.key = key;
		this.meta = meta;
		this.isCallerSensitive = isCallerSensitive;
		this.approvedCaller = approvedCaller;
	}
	
	protected SecretValue(V value, K key, M meta) {
		this.value = value;
		this.key = key;
		this.meta = meta;
		isCallerSensitive = false;
		approvedCaller = null;
	}
}
