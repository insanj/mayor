SHELL:=/bin/bash
JAVA_HOME:=/usr/lib/jvm/jdk1.8.0_131
JAVAC_CMD=/usr/lib/jvm/jdk1.8.0_131/bin/javac
JAR_CMD=/usr/lib/jvm/jdk1.8.0_131/bin/jar

OUTPUT_NAME=mayor
SOURCE_PATH=plugin/java
BUILD_PATH=build
EXTERNAL_PATH=external
SPIGOT_JAR_FILENAME=spigot-1.13.2.jar
CRAFTBUKKIT_JAR_FILENAME=craftbukkit-1.13.2.jar
JAR_DEPS_PATH=$(EXTERNAL_PATH)/$(SPIGOT_JAR_FILENAME):$(EXTERNAL_PATH)/$(CRAFTBUKKIT_JAR_FILENAME)
GIT_TAG:=$(shell git describe --tags)
OUTPUT_VERSIONED_NAME=$(OUTPUT_NAME)-$(GIT_TAG)
SERVER_PATH=server

FIND_JAVA_FILES := $(shell find . -name '*.java')

.PHONY: all
all: plugin server

.PHONY: plugin
plugin:
	# step 1 clean up / erase old version
	-rm -r -f $(BUILD_PATH)
	mkdir $(BUILD_PATH) && mkdir $(BUILD_PATH)/bin
	# step 2 part 2 compile the plugin into the bin dir
	$(JAVAC_CMD) -cp "$(JAR_DEPS_PATH)" -d $(BUILD_PATH)/bin $(FIND_JAVA_FILES)
	# step 3 copy config .yml to a new "build in progress" directory
	-cp -r $(SOURCE_PATH)/*.yml $(BUILD_PATH)/bin/
	# step 4 create JAR file using the "build in progress" folder
	$(JAR_CMD) -cvf $(BUILD_PATH)/$(OUTPUT_VERSIONED_NAME).jar -C $(BUILD_PATH)/bin .

.PHONY: clean
clean:
	# step 5 remove any existing plugin on the server in the server folder
	-rm -r -f $(SERVER_PATH)
	mkdir $(SERVER_PATH)
	echo "eula=true" > $(SERVER_PATH)/eula.txt
	cp -R $(EXTERNAL_PATH)/$(CRAFTBUKKIT_JAR_FILENAME) $(SERVER_PATH)/$(CRAFTBUKKIT_JAR_FILENAME)
	#-rm -r -f $(SERVER_PATH)/plugins/*.jar

.PHONY: server
server:
	# step 6 copy the JAR file into the server to run it!
	-cp -R $(BUILD_PATH)/$(OUTPUT_VERSIONED_NAME).jar $(SERVER_PATH)/plugins/$(OUTPUT_VERSIONED_NAME).jar
	cd $(SERVER_PATH) && java -Xms1G -Xmx1G -jar -DIReallyKnowWhatIAmDoingISwear $(CRAFTBUKKIT_JAR_FILENAME)

.PHONY: nbted
nbted:
	 nbted --print schematics/default.schematic > build/default.schematic.txt
	 # install with cargo; converts any NBT file to a pretty-printed txt (can be converted back if needed)
