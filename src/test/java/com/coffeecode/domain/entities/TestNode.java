package com.coffeecode.domain.entities;

public class TestNode extends NetworkNode {
    private TestNode(TestNodeBuilder builder) {
        super(builder);
    }

    public static class TestNodeBuilder extends AbstractNodeBuilder<TestNodeBuilder> {
        @Override
        public TestNode build() {
            if (this.type == null) {
                this.type(NodeType.JUNCTION);
            }
            return new TestNode(this);
        }
    }

    public static TestNodeBuilder builder() {
        return new TestNodeBuilder();
    }
}
